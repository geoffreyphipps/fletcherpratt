
This program produces ship files for use with a modified version
of the Fletcher Pratt Naval wargame rules. I am planning to issue 
my own derivative, provisionally named "Range Rate."

The main modification is  that all damage is divided by 40 to simplify
the book-keeping during the game. I also changed the formula
used to calculate the value of a ship. After a number of games
it became clear that scenario balance depends heavily on the maximum 
damage that a ship can inflict each turn. That number is the "Balance Points"
value on the ship log. A balanced scenario should have rougly the same number of
balance points on each side. The "Classic Points" are the original Fletcher Pratt
values, "Defensive" is a variant that I think produces more balanced games.

External Dependencies
=====================

This uses two external libraries, which you need to download separately:
1. commons-csv-1.9.0.jar
2. commons-csv-1.9.0-sources.jar


Producing the Logs
==================

There are two main functions, one in CVSReader and one in ProcessAll.
The CSVReader only reads one csv file so is best used when
building up a new CSV file. The ProcessAll is the real entry point.
It takes two arguments:

1. The input directory
2. The outputSafe directory

For every CSV in the input directory it creates a sub-directory
in the outputSafe directory and generates an HTML log file for every
ship into that subdirectory.

This project uses the commons-csv package from apache, download it 
from apache.org,
probably https://commons.apache.org/proper/commons-csv/download_csv.cgi

Running the Program
===================

The main class is: com.gphipps.fletcherpratt.ProcessAll
The program arguments are  "$ROOT/fletcherpratt/input"  "$ROOT/fletcherpratt/output"
where $ROOT is MacOS/Linux syntax for a variable that holds the name of the directory containing this package
For example:


Input Data Files
================

The input ship data is the CSV files in the input directory, 
separated by nationality and class. These include all the Fletcher
Pratt ship features except for tertiary batteries. The tertiaries
are ignored to speed play by removing lots of tiny shots. It might
be necessary to include them if there was a battle involving
both capital ships and torpedo boats, where the battleships large 
number of 3" guns would be useful.

Ships are organized by class

Armor is always taken to be the maximum value for that category in a published source.
For example, belt armor typically tapers and might be quoted
as 3-5 inches. These CSVs use 5".  For early Armored Cruisers
with both "angled" and "flat" deck armor, the flat value is
used for the deck armor.

Displacement is standard displacement in long tons, not full load.
Speed drops fractional knots.

The launched and rebuilt dates are included for ships that
changed dramatically after a rebuild.

The turret layout field shows the layout of the primary armament from
bow to stern, reading from left to right. Each entry shows the number of
guns followed by the arc of fire. The 4 primary arcs are:

* Bow - straight ahead
* Port
* Starboard
* Stern

Each arc covers the obvious 90 degrees, and they do not overlap.
In addition, the following composite arcs exist for guns in
centerline turrets:

* Forward - meaning Bow, Port, and Starboard
* Aft - meaning Stern, Port, and Starboard
* Center-Line - meaning Port and Starboard

Lower case indicates secondary armament. That is only used for destroyers,
because they often mounted their secondary armament center-line. 
For larger classes the secondary armament was always half on each broadside.

So a standard 8-gun ship with 4 turrets, two forward and two aft,
super-firing would be:

2F 2F 2A 2A

if the midships turrets were not super-firing then it would be

2F 2C 2C 2A

The Nelson and Rodney are

2F 2F 2C

If two turrets are at the same distance from the bow (only possible for Port and
Starboard guns) then they are enclosed in parentheses. For example, a destroyer 
with single-guns in centerline mounts, except for one pair of port and starboard
guns:

1F (1P 1S) 1C 1C 1A

Many cruisers had no turreted main battery guns, all were in casemates.
In this case the code is "All casemate" and therefore half the guns fire on each broadside,
with no fore or aft coverage.

All secondaries are assumed to be braodside, therefore the number in the CSV is halved.
