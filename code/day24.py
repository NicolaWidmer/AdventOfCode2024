import networkx as nx
from pyvis.network import Network
from networkx import Graph,DiGraph

f=open("./inputs/day24.in","r")

base,instrs = f.read().split("\n\n")


base_map = {}

for b in base.split('\n'):
    var,num = b.split(": ")
    base_map[var] = int(num)

instr_map = {}

graph = DiGraph()

for instr in instrs.split('\n'):
    a,op,b,_,dest = instr.split()

    graph.add_edge(a,op+"_"+dest)
    graph.add_edge(b,op+"_"+dest)
    graph.add_edge(op+"_"+dest,dest)


    instr_map[dest] = (a,op,b)

def solve(var):
    if var in base_map:
        return base_map[var]
    (a,op,b) = instr_map[var]

    a_s,b_s = solve(a),solve(b)

    if op == 'XOR':
        ans = a_s ^ b_s
    elif op == 'AND':
        ans = a_s&b_s
    elif op == 'OR':
        ans = a_s|b_s

    base_map[var] = ans

    return ans

ans = 0
for i in range(99,-1,-1):
    cur = f"z{i:02d}"
    if cur in instr_map:
        ans = 2*ans + solve(cur)


nt = Network('500px', '500px')
nt.from_nx(graph)
#nt.show('nx.html',notebook=False)

print(ans)

#by hand form pyvis graph
print(",".join(sorted(["z35","fdw","z11","hcc","z05","bpf","qcw","hqc"])))