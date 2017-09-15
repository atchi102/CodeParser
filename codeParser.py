import os
import sys
import re

walk_dir = sys.argv[1]
output_dir = sys.argv[2]
f_content = ""

for root, subdirs, files in os.walk(walk_dir):
        #list_file_path = os.path.join(root, 'my-directory-list.txt')

        #with open(list_file_path, 'wb') as list_file:

        for filename in files:
            if not filename.endswith(".cpp"):
                continue
            file_path = os.path.join(root, filename)

            with open(file_path, 'rb') as f:
                f_content = f.read()
                f.close()

            outputFilePath = output_dir + filename + ".txt"
            with open(outputFilePath, 'w+') as newf:
                cleaning_re = re.compile('\\W|_+|[0-9]')
                camel_case_re = re.compile('(?<=[A-Z])(?=[A-Z][a-z]+)|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])')

                cleaned_content = cleaning_re.sub(' ', f_content)
                finished_content = camel_case_re.sub(' ',cleaned_content)
                newf.write(finished_content)
                newf.close()
