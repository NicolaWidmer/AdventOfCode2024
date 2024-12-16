import heapq

f=open("./inputs/day16.in","r")

grid = f.read().split("\n")

n,m = len(grid),len(grid[0])

print(f' {n} {m}')

x,y = n-2,1

x_e,y_e = 1,m-2

dirs = [(-1,0),(0,1),(1,0),(0,-1)]

heap = [(0,x,y,1,x,y,1)]

vis = [[[(-1,set()) for _ in range(4)] for _ in range(m)] for _ in range(n)]

ans1 = 0
ans2 = 0

while heap:

    dist,x,y,dir,lx,ly,ldir = heapq.heappop(heap)  


    (d,s) = vis[x][y][dir]

    if dist > d and d != -1:
        continue
    

    if (x,y) == (x_e,y_e) and ans1 == 0:
        ans1 = 0

    _,prev_s = vis[lx][ly][ldir]
    next_s = s.union(prev_s)
    next_s.add((x,y))
    vis[x][y][dir] = (dist,next_s)

    dx,dy = dirs[dir]

    if not grid[x+dx][y+dy] == '#':
        heapq.heappush(heap,(dist+1,x+dx,y+dy,dir,x,y,dir))
    heapq.heappush(heap,(dist+1000,x,y,(dir+1)%4,x,y,dir))
    heapq.heappush(heap,(dist+1000,x,y,(dir-1)%4,x,y,dir))

best = vis[x_e][y_e][0][0]
ans_s = set()

for d,s in vis[x_e][y_e]:

    if d <= best:
        best = d
        ans_s = ans_s.union(s)

print(best)
print(len(ans_s))