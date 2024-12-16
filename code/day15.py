import copy

def can_move(grid,x,y,dx,dy):
    while True:
        x,y = x+dx,y+dy
        if grid[x][y] == '#':
            return False
        elif grid[x][y] == '.':
            return True
        
def make_move(grid,x,y,dx,dy):
    x,y = x+dx,y+dy

    if grid[x][y] == '.':
        return
    
    grid[x][y] = '.'
    while True:
        x,y = x+dx,y+dy 
        if grid[x][y] == '.':
            grid[x][y] = 'O'
            return

def solve1(grid,dirs):
    grid = list(map(list,grid.splitlines()))

    n,m = len(grid),len(grid[0])

    x,y = 0,0
    for i in range(n):
        for j in range(m):
            if grid[i][j] == '@':
                x,y = i,j
                grid[i][j] = '.'

    for dir in dirs:
        if dir == '\n':
            continue
        elif dir == '>':
            dx,dy = 0,1
        elif dir == 'v':
            dx,dy = 1,0
        elif dir == '<':
            dx,dy = 0,-1
        elif dir == '^':
            dx,dy = -1,0

        if can_move(grid,x,y,dx,dy):
            make_move(grid,x,y,dx,dy)
            x,y = x+dx,y+dy

    print("\n".join(map(lambda x:"".join(x),grid)))

    ans = 0
    for i in range(n):
        for j in range(m):
            if grid[i][j] == 'O':
                ans += j + 100*i

    return ans

def can_hor(grid,x,y,dx,dy):
    while True:
        x,y = x+dx,y+dy
        if grid[x][y] == '#':
            return False
        elif grid[x][y] == '.':
            return True

def make_hor(grid,x,y,dx,dy):
    x,y = x+dx,y+dy

    if grid[x][y] == '.':
        return
    
    last = grid[x][y]
    grid[x][y] = '.'

    while True:
        x,y = x+dx,y+dy
        if grid[x][y] == '.':
            grid[x][y] = last
            return
        grid[x][y],last = last,grid[x][y]

def can_vert(grid,x,y,dx,dy):
    if grid[x][y] == '#':
        return False
    elif grid[x][y] == '.':
        return True
    
    if grid[x][y] == '[':
        return can_vert(grid,x+dx,y+dy,dx,dy) and can_vert(grid,x+dx,y+dy+1,dx,dy)
    elif grid[x][y] == ']':
        return can_vert(grid,x+dx,y+dy,dx,dy) and can_vert(grid,x+dx,y+dy-1,dx,dy)
    
def which_move(grid,x,y,dx,dy):
    if grid[x][y] == '.':
        return set()
    
    if grid[x][y] == '[':
        s = which_move(grid,x+dx,y+dy,dx,dy)
        s = s.union(which_move(grid,x+dx,y+dy+1,dx,dy))
        s.add((x,y))
        s.add((x,y+1))
        return s
    elif grid[x][y] == ']':
        s = which_move(grid,x+dx,y+dy,dx,dy)
        s = s.union(which_move(grid,x+dx,y+dy-1,dx,dy))
        s.add((x,y))
        s.add((x,y-1))
        return s

def make_vert(grid,x,y,dx,dy):
    moves = which_move(grid,x,y,dx,dy)

    new_gird = copy.deepcopy(grid)

    for [x,y] in moves:
        new_gird[x][y] = '.'

    for [x,y] in moves:
        new_gird[x+dx][y+dy] = grid[x][y]
     

    return new_gird
 

def solve2(grid,dirs):
    grid = list(map(list,grid.replace('#','##').replace('O','[]').replace('.','..').replace('@','@.').splitlines()))

    n,m = len(grid),len(grid[0])

    x,y = 0,0
    for i in range(n):
        for j in range(m):
            if grid[i][j] == '@':
                x,y = i,j
                grid[i][j] = '.'

    for dir in dirs:
        if dir == '\n':
            continue
        elif dir == '>':
            dx,dy = 0,1
        elif dir == 'v':
            dx,dy = 1,0
        elif dir == '<':
            dx,dy = 0,-1
        elif dir == '^':
            dx,dy = -1,0

        if dx == 0:
            if can_hor(grid,x,y,dx,dy):
                make_hor(grid,x,y,dx,dy)
                x,y = x+dx,y+dy
        else:
            if can_vert(grid,x+dx,y+dy,dx,dy):
                grid = make_vert(grid,x+dx,y+dy,dx,dy)
                x,y = x+dx,y+dy
        """
        for i in range(n):
            for j in range(m):
                if x == i and y == j:
                    print('@',end='')
                else:
                    print(grid[i][j],end='')
            print()
        
        #print("\n".join(map(lambda x:"".join(x),grid)))"""

    ans = 0
    for i in range(n):
        for j in range(m):
            if grid[i][j] == '[':
                ans += j + 100*i

    return ans


f=open("./inputs/day15.in","r")

grid,dirs = f.read().split("\n\n")

#print(solve1(grid,dirs))
print(solve2(grid,dirs))