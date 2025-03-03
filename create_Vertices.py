import sys
import numpy as np
import pandas as pd
import networkx as nx
import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif'] = ['SimHei']  # 显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 设置正常显示符号

# 建立结点类
class Vertex:
    def __init__(self, name, dot_weight, dot_place, near_dot):
        self.name = name
        self.dot_weight = dot_weight
        self.dot_place = dot_place
        self.near_dot = near_dot

# 根据信息找到结点的邻接点
def find_neighbors(num_list, neighbor_list):
    neighbors = []
    count = 1
    for num, neighbor in zip(num_list, neighbor_list):
        neighbor_ids = neighbor.split('、')  # 分割邻居结点序号
        for id in neighbor_ids:
            neighbors.append((num, int(id), str(count)))
            count += 1
    return neighbors

# 从excel表中获取结点信息G
def get_vertices(data):
    x_data = data['X_Coordinate']
    y_data = data['Y_Coordinate']
    nums = data['Num']
    names = data['Name']
    connect = data['Connect']
    weights = data['Weight']
    Edges = find_neighbors(nums, connect)

    # 创建字典
    Vertices = {num: (x, y) for num, x, y in zip(nums, x_data, y_data)}

    # 创建图
    G = nx.Graph()
    for node, pos in Vertices.items():
        G.add_node(node, pos=pos)
    for edge in Edges:
        G.add_edge(edge[0], edge[1], road=edge[2])
        pos1 = Vertices[edge[0]]
        pos2 = Vertices[edge[1]]
        distance = np.sqrt((pos1[0] - pos2[0]) ** 2 + (pos1[1] - pos2[1]) ** 2)
        G.edges[edge[0], edge[1]]['length'] = distance

    # 设置节点权重
    node_weights = {num: weight for num, weight in zip(nums, weights)}
    nx.set_node_attributes(G, node_weights, 'weight')

    # 设置节点名称
    node_names = {num: name for num, name in zip(nums, names)}
    nx.set_node_attributes(G, node_names, 'name')

    return G, node_weights, node_names

# 建立结点数组
def create_vertices(data):
    G, node_weights, node_names = get_vertices(data)
    dot = []
    for node in G.nodes():
        near = []
        for edge in G.edges(node):
            if edge[0] == node:
                near.append(edge[1])
            elif edge[1] == node:
                near.append(edge[0])
        dot.append(Vertex(node, node_weights[node], G.nodes[node]['pos'], near))

    return G, dot, node_names

# 加载数据
data_path = r'.\node_data.xlsx'  # 文件路径
data = pd.read_excel(data_path)

# 创建图和结点数据
G, dot, node_names = create_vertices(data)

# 绘制图形，节点大小由权重决定
plt.figure(figsize=(10, 8))

# 根据权重设置节点大小
node_sizes = [G.nodes[node]['weight'] * 50 for node in G.nodes()]  # 放大节点大小以便显示

# 获取节点位置
pos = nx.get_node_attributes(G, 'pos')

# 绘制网络图
nx.draw(G, pos, with_labels=True, labels=node_names, node_size=node_sizes, font_size=10, node_color='skyblue', font_weight='bold', font_family='SimHei')

# 显示图形
plt.title('网络图（节点标签为Name，节点大小按权重）')
plt.show()

