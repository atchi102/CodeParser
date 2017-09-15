import os
import sys
import re

walk_dir = sys.argv[1]
output_dir = sys.argv[2]
f_content = ""

print('walk_dir = ' + walk_dir)
print('output_dir = ' + output_dir)

print('walk_dir (absolute) = ' + os.path.abspath(walk_dir))
print('output_dir (absolute) = ' + os.path.abspath(output_dir))

for root, subdirs, files in os.walk(walk_dir):
    print('--\nroot = ' + root)
    list_file_path = os.path.join(root, 'my-directory-list.txt')
    print('list_file_path = ' + list_file_path)

    with open(list_file_path, 'wb') as list_file:
        for subdir in subdirs:
            print('\t- subdirectory ' + subdir)

        for filename in files:
            if not filename.endswith(".java"):
                continue
            file_path = os.path.join(root, filename)

            print('\t- file %s (full path: %s)' % (filename, file_path))

            with open(file_path, 'rb') as f:
                f_content = f.read()
                f.close()

            outputFilePath = output_dir + filename + ".txt"
            with open(outputFilePath, 'w+') as newf:
                cleaning_re = re.compile('\\W|_+|[0-9]')
                camel_case_re = re.compile('(?<=[A-Z])(?=[A-Z][a-z]+)|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])')

                print(f_content)
                cleaned_content = cleaning_re.sub(' ', f_content)
                print(cleaned_content)
                finished_content = camel_case_re.sub(' ',cleaned_content)
                print(finished_content)
                newf.write(finished_content)
                newf.close()
