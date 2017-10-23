import numpy as np

# define r, matlab, and c/cpp/h counts
r,m,c = np.zeros(100,dtype=np.float),np.zeros(100,dtype=np.float),np.zeros(100,dtype=np.float)
r_counts,m_counts,c_counts = 0,0,0
r_entropy,m_entropy,c_entropy = 0,0,0

f = open('topic_composition-AllFiles.txt','r')

for l in f:
    line = l.strip().split('\t')
    file_name = line[1].lower()
    topics = line[2:]
    topics = np.array(map(float,topics))

    if file_name.endswith('.r.txt'):
        r += topics
        r_entropy += -np.sum(topics*np.log2(topics))
        r_counts += 1
    elif file_name.endswith('.m.txt'):
        m += topics
        m_entropy += -np.sum(topics*np.log2(topics))
        m_counts += 1
    elif file_name.endswith('.c.txt') or file_name.endswith('.cpp.txt') or file_name.endswith('.h.txt'):
        c += topics
        c_entropy += -np.sum(topics*np.log2(topics))
        c_counts += 1

# normalized average entropy
print 'R:',(r_entropy/float(r_counts))/np.log2(100)
print 'M:',(m_entropy/float(m_counts))/np.log2(100)
print 'C:',(c_entropy/float(c_counts))/np.log2(100)

r /= float(r_counts)
m /= float(m_counts)
c /= float(c_counts)

r_t,m_t,c_t = [],[],[]
for i in range(r.shape[0]):
    if r[i] >= m[i] and r[i] >= c[i]:
        r_t.append(i)
    if m[i] >= r[i] and m[i] >= c[i]:
        m_t.append(i)
    else:
        c_t.append(i)

print 'R topics:',r_t
print 'M topics:',m_t
print 'C topics:',c_t
