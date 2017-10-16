import numpy as np
import matplotlib.pyplot as plt

n_groups = 8
fileTypes = ['Rd','R','C/Cpp','H/Hpp','Txt','PDF',
            'Txt','M']
all_files = [202887,163978+14342,11200+10070,18151+15039,90959,15912,
            230890, 187938]

r = [0]*len(all_files)
matlab = [0]*len(all_files)

r[:-2] = all_files[:-2]
matlab[-2:] = all_files[-2:]

fig, ax = plt.subplots()

index = np.arange(n_groups)
bar_width = 0.5

opacity = 0.5
error_config = {'ecolor': '0.3'}

plt.bar(index,r, bar_width,
                 alpha=opacity,
                 color='r',
                 error_kw=error_config,
                 label='R')
plt.bar(index, matlab, bar_width,
                 alpha=opacity,
                 color='b',
                 error_kw=error_config,
                 label='MATLAB')

plt.xlabel('File Type')
plt.ylabel('Number of Files')
plt.title('Number of Files by Type')
plt.xticks(index, fileTypes)
plt.legend()

plt.tight_layout()
plt.show()
