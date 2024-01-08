# ToyLanguageInterpreter
### A custom "toy" language interpreter built on Java

-----

# Used Concepts
- Layered architecture
- JavaFX for GUI
- Encapsulation, interfaces, streams
- MVC pattern

---

# Instructions:
- logical expressions
- arithmetical expressions
- relational expressions
- variable declaration & assignment
- printing
- if
- while
- file opening, closing and reading
- fork (multi-threading)
- heap allocation, reading and writing

---

# Variables Types:
- Bool
- Int
- String
- Reference

---

# Functionalities
- Storing instructions in execution stacks
- Storing local variables in symbol tables
- Storing BufferedReader objects into a file table used for file operations
- Shared heap across all the states created by a program - allocation, reading, writing and garbage collector
- Storing printing output in an output table
- While forking, a new program state is being created with a unique ID 
- Program states: each state has a unique ID, a symbol table and an execution stack

---

# Demo
- Examples Window:
![Screenshot 2024-01-08 125646](https://github.com/Tuddy09/ToyLanguageInterpreter/assets/115088565/099c122c-6dc1-4035-97a1-3ed5c9d19714)
![Screenshot 2024-01-08 125700](https://github.com/Tuddy09/ToyLanguageInterpreter/assets/115088565/f775edfb-c1f1-4e09-a651-b7edb8c3983b)


