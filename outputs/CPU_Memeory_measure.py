import psutil
import pandas as pd
import time



init_time = time.time()
result = pd.DataFrame(columns=["time", "CPU_use", "Memeory_free"])

while (True):
  new_row = pd.Series({"time": time.time(), "CPU_use": psutil.cpu_percent(interval=2), "Memeory_free": psutil.virtual_memory().available})
  result = result.append(new_row, ignore_index=True)
  if (time.time() - init_time > 60):
    init_time = time.time()
    print(time.time() , "saved")
    result.to_csv('Time_vs_CPU_Memeory.csv')
