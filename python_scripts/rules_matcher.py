import yara
import os
# Only yara-python should be installed and not pip install yara
# Pip uninstall yara  for the win 

def fire_rules(rules_path, binary_name, payload_folder):
    # Iterate over all rules and save them into a list
    rules_list = []
    matches_list = []
    matches_list_dup = []
    for filename in os.listdir(rules_path):
        file_name, file_extension = os.path.splitext(filename)
        file_path = os.path.join(rules_path,filename)
        if "yar" in file_extension:
            rules_list.append(file_path)
    
    # iterate trough the Payload folder and get all the important files, for every important file match all the rules
    for path, currentDirectory, files in os.walk(payload_folder):
        for file in files:
            file_path = os.path.join(path, file)
            file_name, file_extension = os.path.splitext(file_path)
            if "plist" in file_extension or binary_name == os.path.basename(os.path.normpath(file_name)):
                for x in rules_list:
                    rules = yara.compile(x)
                    matches = rules.match(file_path)
                    if matches:
                        matches_list.append(matches)
          
    # Create another list without duplicates naive method  cuz i'm lazy          
    for x in matches_list:
        if x not in matches_list_dup:
            matches_list_dup.append(x)

    return matches_list_dup
