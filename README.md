# records

A clojure exercise in creating an application for reading and displaying data from differently formatted files.

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar records-0.1.0-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Captain's Log

#### Day 1
Explored creating my own delimited file parser with the desire to lazily parse files one line at a time.
Implemented a basic parser with `line-seq`, but `with-open` closes the reader prematurely so `doall` was necessary,
defeating the point of trying to make it lazy.  Would need to use another mechanism to close reader.
Found useful library [here](https://github.com/davidsantiago/clojure-csv) that allows you to specify a delimiter. 
Claims to be lazy in case of large files.

First hurdle- The library mentioned above only lazily parses a string, but does not read files lazily.  So this means
 that even in the case of a large file, we have to bring the file into memory as a string. We can stream the file, but 
 that stream must remain open while it gets parsed, and `with-open` closes the stream before the lazy sequence is 
 consumed.  
 
Finally got basic parsing of multiple different file types into one set of data before streams close.
For now, I am manually calling `.close` on all readers (instead of using with-open), need to clean
that up.

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
