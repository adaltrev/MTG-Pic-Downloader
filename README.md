# MTG Pic Downloader
A simple software that downloads high-resolution images of "Magic: The Gathering" cards.
Given a text file with a list of card names, MTG Pic Downloader fetches from Scryfall a .png file for each one of those cards and saves them separately in a folder.

## Usage and Input Formatting
Run the .jar file in the same folder of the input file. By default, the program will read from "source.txt" and save all images in "images/deck".
The input file needs to have all card names written on separate lines, case insensitive, with or without a number before the name.
For split or double-faced cards, use either the full name (Fire // Ice) or just the first part (Fire). Both faces of a double-faced card are downloaded as separate files.

## Run Options
  * Run with default settings
```
java -jar  mtg-pic.jar
```
  * Run with custom sub-folder name (cards will be saved in images/folder_name)
```
java -jar  mtg-pic.jar folder_name
```
  * Run with custom sub-folder name and custom input file name
```
java -jar  mtg-pic.jar folder_name file_name
```
