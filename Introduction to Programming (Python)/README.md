# Conway's Game of Life (Python Implementation)

This project is a Python implementation of **Conway's Game of Life**, a zero-player cellular automaton where cells evolve through generations based on a set of simple rules.  
It was developed collaboratively with [@Alviona M](https://github.com/alvionaM) for an assignment of the 1st semester's Introduction to Computer Science class.  

---

## Features
- Board creation of size `n x n`
- Functions to set cells alive or dead
- Utility functions:
  - Copy the board
  - Iterate through cells
  - Print the board with Unicode squares (⬛ for alive, ⬜ for dead)
  - Get cell neighbors
- Placement of classic patterns:
  - **Blinker**
  - **Glider**
- `tick()` function to move the game forward by one generation
- Example `__main__` loop to play the game for 100 generations with a given starting setup

---

## Example Output

A `5x5` board with some alive cells looks like this:  
⬛⬜⬜⬜⬛   
⬜⬜⬜⬜⬜   
⬜⬜⬛⬜⬜   
⬜⬜⬜⬜⬛   
⬜⬜⬜⬜⬛   

---

## To run the Game

```
python game_of_life.py   
```

By default, it runs with an initial **blinker** and **glider** and evolves for **100 generations** with a short delay between each step.

---

## Rules Recap

1. Any live cell with fewer than **2 live neighbors** dies (*underpopulation*).  
2. Any live cell with **2 or 3 live neighbors** survives.  
3. Any live cell with more than **3 live neighbors** dies (*overpopulation*).  
4. Any dead cell with **exactly 3 live neighbors** becomes alive (*reproduction*).  


