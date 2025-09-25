"""Conway\'s Game of Life."""

def board(n):
    """Constructor of the game's board.

    n -- parameter of board's dimentions.

    Constructs a representation of the game's board with n*n cells,
    in which none of the cells are alive.

    Returns representation of the game's board which is a dictionary 
    with n*n elements.
    Each cell corresponds to an element with a key to the tuple (i,j),
    with i being the cell's row number and j the column number.
    The cells of rows and columns are numbered from 0 to n-1.
    The value of an element is True or False depending on whether the cell
    is alive or not.

    Examples:

    >>> game = board(3)
    >>> len(game)
    9
    >>> game == {(0, 0): False, (0, 1): False, (0, 2): False, (1, 0): False, (1, 1): False, (1, 2): False, (2, 0): False, (2, 1): False, (2, 2): False}
    True
    >>> game[2,1]
    False
    """
    return { (i,j): False for i in range(n) for j in range(n) }



def is_alive(board, p):
    """Checks if a cell is alive.

    board -- the board of the game in which the cell p is in.
    p -- the position of the cell.

    The value of p is a tuple of the form (i,j).
    It returns True if the cell is alive, else it returns False.

    Example:

    >>> is_alive(board(4), (3,2))
    False
    """
    return board[p] 



def set_alive(board, p, alive):
    """Gives life to a cell or it removes it from the cell.

    board -- the board of the game in which the cell p is in.
    p -- the position of the cell.
    alive -- Boolean value.

    The cell turns alive if alive==True. 
    If alive==False, the cell dies.

    Example:

    >>> game = board(4)
    >>> is_alive(game, (3,2))
    False
    >>> set_alive(game, (3,2), True)
    >>> is_alive(game, (3,2))
    True
    >>> set_alive(game, (3,2), False)
    >>> is_alive(game, (3,2))
    False
    """
    board[p]=alive
    


    
def get_size(board):
    """Size of the game's board.

    board -- the game's board.

    Returns n if board represents an n*n board.

    Paradeigmata:

    >>> get_size(board(4))
    4
    >>> get_size(board(10))
    10
    """
    from math import sqrt
    return int(sqrt(len(board)))


def copy_board(board):
    """Copy of the game's board.

    board -- the game's board.

    Returns a new board that is a copy of board.

    Examples:

    >>> game = board(10)
    >>> set_alive(game, (4,7), True)
    >>> game2 = copy_board(game)
    >>> game2 is game
    False
    >>> is_alive(game2, (4,7))
    True
    """
    n=get_size(board)
    copy={ (i,j): board[(i,j)] for i in range(n) for j in range(n) }
    return copy


def get_iterator(board):
    """Iterator for scanning the elements of  the game's board.

    board -- the game's board.
    
    Returns an iterator that gives the board's cells starting from
    these of line 0: (0,0), (0,1), (0,2),..., then following these of 
    line 1: (1,0), (1,1), (1,2),... etc. until there aren't any cells left.
    For each cell, the iteration returns the position of it and a boolean value 
    True or False depending on whether it is alive or not.

    Example:
    >>> game = board(3)
    >>> set_alive(game, (2, 1), True)
    >>> for cell in get_iterator(game):
    ...     print(cell)
    ... 
    ((0, 0), False)
    ((0, 1), False)
    ((0, 2), False)
    ((1, 0), False)
    ((1, 1), False)
    ((1, 2), False)
    ((2, 0), False)
    ((2, 1), True)
    ((2, 2), False)
    """
    row, col = 0, 0
    n=get_size(board)-1
    while row<=n and col<=n:
        yield ((row,col), board[(row,col)])
        if col < get_size(board)-1:
            col+=1
        else: #eftase sto telos ths grammhs 
            row = row + 1
            col = 0 
            
        



#---------------------- Askisi 5 ------------------------
def print_board(board):
    """Print the game's board.

    board -- the game's board.

    Prints the game's board for which with a the black square (unicode 11035 character) 
    we get the alive squares and with the white square (unicode 11036 character) 
    we get the dead ones.

    Example:
    >>> game = board(5)
    >>> set_alive(game, (0,0), True)
    >>> set_alive(game, (2,2), True)
    >>> set_alive(game, (4,4), True)
    >>> set_alive(game, (3,4), True)
    >>> set_alive(game, (0,4), True)
    >>> print_board(game)
    ⬛⬜⬜⬜⬛
    ⬜⬜⬜⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬛
    ⬜⬜⬜⬜⬛
    """ 
    n=get_size(board)-1
    for cell in get_iterator(board):
        print(chr(11035), end='') if cell[1]==True else print(chr(11036), end='')
        if cell[0][1]== n: #end of the line
            print()


#---------------------- Askisi 6 ------------------------
def neighbors(p):
    """Neighbour cells.

    p -- the position of the cell.

    Returns a set that contains the positions of p's 8 neighbour cells. 
    p isn't included in the set.

    Examples:
    >>> neighbors((3,2)) == {(3, 3), (3, 1), (2, 1), (2, 3), (4, 3), (2, 2), (4, 2), (4, 1)}
    True
    >>> neighbors((0,0)) == {(0, 1), (-1, 1), (-1, 0), (-1, -1), (0, -1), (1, 0), (1, -1), (1, 1)}
    True
    >>> neighbors((0,10)) == {(-1, 9), (1, 10), (-1, 11), (0, 11), (-1, 10), (1, 9), (0, 9), (1, 11)}
    True
    """
    neighb={ (i,j) for i in range(p[0]-1, p[0]+2) for j in range(p[1]-1, p[1]+2) if (i,j)!=p}
    return neighb

def place_blinker(board, p = (0,0)):
    """Placement of blinker.

    board -- the board of the game in which the cell p is in.
    p -- the position of the cell.

    Places 3 living organismouson the board in nei cells of the same column,
    starting from the position p and moving on downwards.
    
    Examples:
    >>> game = board(5)
    >>> place_blinker(game)
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬜⬜⬜
    ⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜
    >>> place_blinker(game, (1,2))
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜
    ⬛⬜⬛⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬜
    >>> place_blinker(game, (4,4))
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜
    ⬛⬜⬛⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬛
    """
    n=get_size(board)-1
    col=p[1]
    row=p[0]
    i=0
    while row + i <= n and i<=2:
        set_alive(board, (row+i, col), True)
        i=i+1


def place_glider(board, p = (0,0)):
    """Here we place a glider.

    board -- the board of the game in which the cell p is in.
    p -- the position of the cell.

    Places 5 living organisms on the board in the motif of a glider
    starting from the location p, like the example below.
    Cell p is not alive.
    
    Example:
    >>> game = board(7)
    >>> place_glider(game)
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    >>> place_glider(game, (3,3))
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬛⬜⬛⬜
    ⬜⬜⬜⬜⬛⬛⬜
    ⬜⬜⬜⬜⬜⬜⬜
    """
    n=get_size(board)-1
    col=p[1]
    row=p[0]
    if row+1<=n:
        set_alive(board, (row+1, col), True)
    if row+2<=n and col+1<=n:
        set_alive(board, (row+2, col+1),True)
    if col+2<=n: 
        i=0
        while i<=2 and row+i<=n:
            set_alive(board, (row+i, col+2), True)
            i=i+1

def tick(board):
    """Moves the game one step towards the next generation.

    board -- the game's board.

    Changes the board moving it a generation forward,
    according to the rules of the Game Of Life.

    Example:

    >>> game = board(6)
    >>> place_glider(game)
    >>> place_blinker(game, (3,4))
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜
    ⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬜⬛⬜
    >>> tick(game)
    >>> print_board(game)
    ⬜⬛⬜⬜⬜⬜
    ⬜⬜⬛⬛⬜⬜
    ⬜⬛⬛⬛⬜⬜
    ⬜⬜⬜⬛⬜⬜
    ⬜⬜⬜⬛⬛⬛
    ⬜⬜⬜⬜⬜⬜
    """
    n=get_size(board)-1
    copy=copy_board(board)
    for item in get_iterator(copy):
        cell=item[0]
        i=0 #counter of alive neighbour cells
        for neighb in neighbors(cell):
            if 0<=neighb[0]<=n and 0<=neighb[1]<=n:
                if is_alive(copy, neighb):
                    i+=1
        if is_alive(copy,cell):
             if i<=1:
                set_alive(board, cell, False)
             #if i==2 or i==3 then the cell remains alive (aka exactly as it was).
             elif i>=4:
                set_alive(board, cell, False)
        else:
             if i==3:
                set_alive(board, cell, True)
                
            

if __name__ == '__main__':
    """Plays the game for a particular initial placement, for 
    100 generations. The game's board appears on each step.
    """
    # initial board
    game = board(10)
    place_blinker(game, (1,2))
    place_glider(game, (2,4))

    from time import sleep
        

    for i in range(100): 
        print_board(game) #the board of the initial placement is also printed
        tick(game)
        sleep(2)
        print()
        print()

