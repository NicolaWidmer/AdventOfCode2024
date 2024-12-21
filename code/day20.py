f=open("./inputs/day20.in","r")

grid = f.read().split("\n")

n,m = len(grid),len(grid[0])

print(f'{n} {m}')

for i in range(n):
    for j in range(m):
        if grid[i][j] == 'S':
            x,y = i,j
        elif grid[i][j] == 'E':
            x_e,y_e = i,j

dirs = [(-1,0),(0,1),(1,0),(0,-1)]

dist = [[-1 for _ in range(m)] for _ in range(n)]

d = 0
dist[x][y] = d

while x != x_e or y != y_e:
    for dx,dy in dirs:
        if (not grid[x+dx][y+dy] == '#') and dist[x+dx][y+dy] == -1:
            x,y = x+dx,y+dy
            break
    d += 1
    dist[x][y] = d


def saved_time(i,j,di,dj):
    new_i,new_j = i+di,j+dj
    if new_i < 0 or new_i >= n or new_j < 0 or new_j >= m:
        return 0
    if dist[new_i][new_j] == -1:
        return 0
    if dist[new_i][new_j] < dist[i][j]:
        return 0
    
    return dist[new_i][new_j] - dist[i][j] - abs(di) - abs(dj)

min_save = 100

max_len = 2
ans = 0

for i in range(n):
    for j in range(m):
        if grid[i][j] == '#':
            continue

        for di in range(max_len+1):
            for dj in range(max_len+1-di):
                if saved_time(i,j,di,dj) >= min_save:
                    ans += 1
                if di != 0 and saved_time(i,j,-di,dj) >= min_save:
                    ans += 1
                if dj != 0 and saved_time(i,j,di,-dj) >= min_save:
                    ans += 1
                if di != 0 and dj != 0 and saved_time(i,j,-di,-dj) >= min_save:
                    ans += 1
                
print(ans)

max_len = 20

ans = 0
for i in range(n):
    for j in range(m):
        if grid[i][j] == '#':
            continue

        for di in range(max_len+1):
            for dj in range(max_len+1-di):
                if saved_time(i,j,di,dj) >= min_save:
                    ans += 1
                if di != 0 and saved_time(i,j,-di,dj) >= min_save:
                    ans += 1
                if dj != 0 and saved_time(i,j,di,-dj) >= min_save:
                    ans += 1
                if di != 0 and dj != 0 and saved_time(i,j,-di,-dj) >= min_save:
                    ans += 1
                
print(ans)


