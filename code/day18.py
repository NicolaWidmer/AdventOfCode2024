from collections import deque

f=open("./inputs/day18.in","r")

blocks = list(map(lambda x : (int(x.split(',')[0]),int(x.split(',')[1])),f.read().split("\n")))

dim = 71

num_bytes = 1024

def dist_with_bytes(num_bytes):

    blocks_set = set(blocks[:num_bytes])

    dists = [[-1] * dim for _ in range(dim)]

    queue = deque([(0,0,0)])
    dirs = [(0,1),(1,0),(0,-1),(-1,0)]

    while queue:
        (x,y,d) = queue.popleft()
        if dists[x][y] == -1:
            dists[x][y] = d
            for dx,dy in dirs:
                if x+dx < dim and x+dx >= 0 and y+dy < dim and y+dy >= 0 and (not (x+dx,y+dy) in blocks_set):
                    queue.append((x+dx,y+dy,d+1))
    return dists[dim-1][dim-1]

print(dist_with_bytes(num_bytes))

l,r = 0,len(blocks)

while r-l > 1:
    m = (r+l)//2
    if dist_with_bytes(m) == -1:
        r = m
    else:
        l = m

x_ans,y_ans = blocks[l]
print(f'{x_ans},{y_ans}')
