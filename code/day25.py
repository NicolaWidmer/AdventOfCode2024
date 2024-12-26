f=open("./inputs/day25.in","r")

keys = []
locks = []

for block in f.read().split("\n\n"):
    block = block.splitlines()
    cur = [-1]*5
    for i in range(5):
        for j in range(7):
            if block[j][i] == '#':
                cur[i] += 1
    
    if block[0][0] == '#':
        keys.append(cur)
    else:
        locks.append(cur)


print(sum([all([x+y<6 for (x,y) in zip(k,l)]) for k in keys for l in locks])) 
