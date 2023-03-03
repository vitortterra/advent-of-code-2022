from __future__ import annotations
from typing import Dict

import sys


ROOT_DIR: str = "/"
SIZE_LIMIT: int = 100000
TOTAL_SPACE: int = 70000000
REQUIRED_FREE_SPACE: int = 30000000

class FileNode:
    def __init__(
        self, 
        name: str,
        size: int = 0, 
        children: Dict[str, FileNode] = None, 
        parent: FileNode = None):

        self.name = name
        self.size = size
        self.children = {} if children is None else children
        self.parent = parent

class TreeParser:
    def __init__(self):
        self.current_node: FileNode = None
        self.root: FileNode = None

    def parse_input(self) -> FileNode:
        for line in sys.stdin:
            match line.split():
                case ["$", "cd", ".."]:
                    self._cddotdot()
                case ["$", "cd", dir_name]:
                    self._cd(dir_name)
                case ["$", "ls"]:
                    pass
                case ["dir", dir_name]:
                    self._new_dir(dir_name)
                case [size_str, file_name]:
                    self._new_file(int(size_str), file_name)
                case _:
                    raise ValueError(f"Unexpected command {line}")

        return self.root
    
    def _cddotdot(self):
        self.current_node = self.current_node.parent
    
    def _cd(self, dir_name: str):
        if dir_name == ROOT_DIR:
            self.root = FileNode(ROOT_DIR)
            self.current_node = self.root
        else:
            self.current_node = self.current_node.children[dir_name]

    def _new_dir(self, dir_name: str):
        self.current_node.children[dir_name] = FileNode(
            dir_name, 
            parent=self.current_node
        )
    
    def _new_file(self, size: int, file_name: str):
        self.current_node.children[file_name] = FileNode(
            file_name,
            size=size,
            parent=self.current_node
        )

def compute_sizes(node: FileNode) -> int:
    for child in node.children.values():
        node.size += compute_sizes(child)

    return node.size

def all_dir_sizes(node: FileNode) -> List[int]:
    if node.is_file():
        return []

    return [
        sz for nd in node.children.values()
        for sz in all_dir_sizes(nd) 
    ] + [node.size]

def main():
    root: FileNode = TreeParser().parse_input()

    compute_sizes(root)

    all_sizes: List[int] = all_dir_sizes(root)
    sizes_sum: int = sum([s for s in all_sizes if s <= SIZE_LIMIT])
    
    print(f"Part 1: {sizes_sum}")

    free_space: int = TOTAL_SPACE - root.size 
    space_demand: int = REQUIRED_FREE_SPACE - free_space
    deleted_dir_size: int = min([s for s in all_sizes if s >= space_demand])

    print(f"Part 2: {deleted_dir_size}")


if __name__ == "__main__":
    main()
