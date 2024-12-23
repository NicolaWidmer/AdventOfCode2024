from networkx import Graph,find_cliques

f=open("./inputs/day23.in","r")

lines = list(map(lambda x:x.split('-'),f.read().split("\n")))

g = {}

graph = Graph()

for u,v in lines:

    if not u in g:
        g[u] = []
    if not v in g:
        g[v] = []

    g[u].append(v)
    g[v].append(u)

    graph.add_edge(u, v)

def num_triangs(start):
    ans = 0
    for n2 in g[start]:
        for n3 in g[n2]:
            for n4 in g[n3]:
                if n4 != start:
                    continue
                div = 1
                if n2[0] == 't':
                    div += 1
                if n3[0] == 't':
                    div += 1

                ans += 1/(2*div)
    return ans


ans = 0
for s in g:
    if s[0] == 't':
        ans += num_triangs(s)

print(ans)


cliques = find_cliques(graph)
bigest = sorted(list(max(cliques, key=len)))

print(",".join(bigest))

    