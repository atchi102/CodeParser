import numpy as np
import matplotlib.pyplot as plt


n_groups = 6
fileTypes = ['Rd','R','C','Cpp',
            'Txt','M']
means_men = [202887,163978+14342,11200,10070,
            230890, 187938]

means_women = (25, 32, 34, 20)

fig, ax = plt.subplots()

index = np.arange(n_groups)
bar_width = 0.5

opacity = 0.5
error_config = {'ecolor': '0.3'}

rects1 = plt.bar(index, means_men, bar_width,
                 alpha=opacity,
                 color=['r','r','r','r','b','b'],
                 error_kw=error_config,
                 label='Men')

plt.xlabel('File Type')
plt.ylabel('Number of Files')
plt.title('Number of Files by Type')
plt.xticks(index, fileTypes)
plt.legend()

plt.tight_layout()
plt.show()
