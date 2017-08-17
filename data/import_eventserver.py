"""
Import sample data for classification engine
"""

import predictionio
import argparse
import csv
import math

def import_events(client, file):
  f = open(file, 'r')
  count = 0
  print "Importing data..."
  dataset = csv.DictReader(f)
  for row in dataset:
    if row['Sex']=='male':
       sex = 0
    else:
       sex = 1

    if not row['Age']:
        row['Age'] = 0

    event = {"target": int(row['Survived']), "class": int(row['Pclass']), "sex": int(sex), "age": int(math.floor(float(row['Age'])))}
    print("[INFO] Processing entry: {count}".format(count=count))

    client.create_event(
	   event="$set",
       entity_type="survivor",
       entity_id=str(count),
       properties=event)
    count += 1
  f.close()
  print "%s events are imported." % count

if __name__ == '__main__':
  parser = argparse.ArgumentParser(
    description="Import sample data for classification engine")
  parser.add_argument('--access_key', default='invald_access_key')
  parser.add_argument('--url', default="http://localhost:7070")
  parser.add_argument('--file', default="./data/sample_decision_trees.txt")

  args = parser.parse_args()
  print args

  client = predictionio.EventClient(
    access_key=args.access_key,
    url=args.url,
    threads=5,
    qsize=500)
  import_events(client, args.file)
