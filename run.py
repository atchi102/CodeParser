# find . -type f | sed 's/.*\.//' | sort | uniq -c > r_file_stats.txt

f = open('matlab_file_stats.txt','r')
o = open('matlab_file_out.txt','w')
x = []
for line in f:
    l = line.strip()
    i = l.find(' ')
    num = int(l[:i])
    ext = l[i:].strip()

    if num > 100:
        x.append([num,ext])


x.sort()

for i in reversed(x):
    o.write(str(i[0]) +' | '+ i[1] +'\n')
