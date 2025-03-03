import json

# 加载原始数据
with open('cars_info.json', 'r') as file:
    data = json.load(file)

# 创建一个列表，用来存储每个车辆的数据
grouped_data = []

# 遍历数据，按 car_num 分组路径
for entry in data:
    car_num = entry['car_num']

    # 查找该车是否已经在列表中
    car_found = False
    for car in grouped_data:
        if car["car_num"] == car_num:
            car["paths"].append(entry["path"])  # 将路径添加到该车
            car_found = True
            break

    # 如果该车没有出现过，就添加新的车辆信息
    if not car_found:
        grouped_data.append({
            "car_num": car_num,
            "speed": entry["speed"],
            "paths": [entry["path"]]
        })

# 将整理好的数据写入新文件
with open('end_cars_info.json', 'w') as outfile:
    json.dump(grouped_data, outfile, indent=4)

