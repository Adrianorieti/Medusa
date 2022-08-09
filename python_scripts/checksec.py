import json
import sys
import ios_checksec
import rules_matcher
import os
# Save the old stdout
original_stdout = sys.stdout 

def getName(binary_path):
  return os.path.basename(os.path.normpath(binary_path))

def create_matches_json(matches):
  
  # create an unique list with all elements instead of a list of lists (to change)
  mylist = []
  for x in matches:
    for y in x:
      mylist.append("{}".format(y))

  # create json object
  json_obj = {"matches": mylist}
  y = json.dumps(json_obj)
  parsed = json.loads(y)

 # Write json to file
  with open('matched.json', 'w') as f:
      sys.stdout = f # Change the standard output to the file we created.
      print(json.dumps(parsed, indent=4, sort_keys=True))
      sys.stdout = original_stdout # Reset the standard output to its original value

def create_flags_json(to_insert):

    # create json object
    json_obj = {"flags": to_insert}
    y = json.dumps(json_obj)
    parsed = json.loads(y)
   
    # Write json to file
    with open('flags.json', 'w') as f:
        sys.stdout = f # Change the standard output to the file we created.
        print(json.dumps(parsed, indent=4, sort_keys=True))
        sys.stdout = original_stdout # Reset the standard output to its original value

def main(binary_path,rules_path,payload_folder_path):
    # checksec on the binary
    checksec = ios_checksec.check(binary_path)
    # generate json for flags
    create_flags_json(checksec)
    # fire all yara rules 
    matches_list = rules_matcher.fire_rules(rules_path, getName(binary_path),payload_folder_path)
    # generate json for matches
    create_matches_json(matches_list)

if __name__ == "__main__":
    try:
      binary_path = sys.argv[1]
      rules_path = sys.argv[2]
      payload_folder_path = sys.argv[3]
      main(binary_path,rules_path,payload_folder_path)
    except KeyError:
      print('Error no arguments were specified')

