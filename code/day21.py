f=open("./inputs/day21.in","r")

nums = f.read().split("\n")

locs = {'7' : (0,0), '8': (1,0), '9': (2,0), '4': (0,1), '5': (1,1), '6': (2,1), '1': (0,2), '2': (1,2), '3': (2,2), '0': (1,3), 'A' :(2,3)}



levels = 25

dps = [{} for _ in range(levels)]

def level_0(loc1,loc2):
        x1,y1 = loc1
        x2,y2 = loc2
        if x1 < x2:
            l1 = (2,1)
            n1 = x2-x1
        else:
            l1 = (0,1)
            n1 = x1-x2

        if y1 < y2:
            l2 = (1,1)
            n2 = y2-y1
        else:
            l2 = (1,0)
            n2 = y1-y2
        #print(f'{n1} {n2}')

        if n1 == 0 and n2 == 0:
            ans = 1
        elif n1 == 0:
            ans = level((2,0),l2,0) + level(l2,(2,0),0) + n2 + 1
        elif n2 == 0:
            ans = level((2,0),l1,0) + level(l1,(2,0),0) + n1 + 1
        elif x1 == 0 and y2 == 3:
            ans = level((2,0),l1,0) + level(l1,l2,0) + level(l2,(2,0),0) + n1 + n2 + 1
        elif x2 == 0 and y1 == 3:
            ans = level((2,0),l2,0) + level(l2,l1,0) + level(l1,(2,0),0) + n1 + n2 + 1
        else:
             ans = min(level((2,0),l1,0) + level(l1,l2,0) + level(l2,(2,0),0),level((2,0),l2,0) + level(l2,l1,0) + level(l1,(2,0),0)) + n1 + n2 + 1

         
        return ans


def level(loc1,loc2,lev):
        if lev == levels-1:
             return last_level(loc1,loc2)
        
        if (loc1,loc2) in dps[lev]:
             return dps[lev][(loc1,loc2)]
        x1,y1 = loc1
        x2,y2 = loc2
        if x1 < x2:
            l1 = (2,1)
            n1 = x2-x1
        else:
            l1 = (0,1)
            n1 = x1-x2

        if y1 < y2:
            l2 = (1,1)
            n2 = y2-y1
        else:
            l2 = (1,0)
            n2 = y1-y2

        #print(f'l1 {n1} {n2}')

        if n1 == 0 and n2 == 0:
            ans = 1
        elif n1 == 0:
            ans = level((2,0),l2,lev+1) + level(l2,(2,0),lev+1) + n2
        elif n2 == 0:
            ans = level((2,0),l1,lev+1) + level(l1,(2,0),lev+1) + n1
        elif x1 == 0 and y2 == 0:
            ans = level((2,0),l1,lev+1) + level(l1,l2,lev+1) + level(l2,(2,0),lev+1) + n1 + n2
        elif x2 == 0 and y1 == 0:
            ans = level((2,0),l2,lev+1) + level(l2,l1,lev+1) + level(l1,(2,0),lev+1) + n1 + n2
        else:
             ans = min(level((2,0),l2,lev+1) + level(l2,l1,lev+1) + level(l1,(2,0),lev+1),level((2,0),l1,lev+1) + level(l1,l2,lev+1) + level(l2,(2,0),lev+1)) + n1 + n2

        dps[lev][(loc1,loc2)] = ans
        return ans


def last_level(loc1,loc2):
        x1,y1 = loc1
        x2,y2 = loc2
        #print(f'{loc1} -> {loc2}  {abs(x1-x2) + abs(y1-y2)}')
        return abs(x1-x2) + abs(y1-y2)

ans = 0

for n in nums:
    cur = 0
    last = 'A'

    for c in n:
        cur += level_0(locs[last],locs[c])
        last = c

    print(cur)

    ans += cur * int(n[:3])


print(ans)
