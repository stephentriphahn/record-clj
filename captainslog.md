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

Side note- learned how to properly spell delimiter

#### Day 2

Now that the file are being efficiently read and returned as one lazy sequence of data, time to move on to rendering the
views.  My first instinct is to implement a multimethod which dispatches on the type of view: Gender, LastName, 
or Date of Birth.  

Sorting and organizing the data is pretty straightforward.  Sorting by birthday required a bit of Date parsing, and 
the gender view required grouping by gender, sorting, then concatenating.  Had to use a custom comparator in the sort-by
function to sort by LastName in ascending order.  

I am thinking of passing in a render-function to the render multi-method.  That would make it more testable, and would 
separate the data sorting logic and the rendering (println) logic.  

Moving on to wiring everything together. The requirements specify a REST api 
be built as well in the same codebase.  Should the `-main` function run the 
logic to display the veiws on the terminla AND start up the web server? Or 
should this be a flag when running the app from the command line?  for now, I
 decided to have the main function read the files and output all 3 views of 
 the data, and then start the web server.  This logic should be easy to 
 change later.
 
 
 
 #### Day 3
 
 Realizing that I need a uniform way of validating input files/lines that the
  REST api and the CLI can both use.  Things break, for example, if one of 
  the lines in a file does not contain all fields.  Zipmap will not cleanly 
  associate the fields with the values, leading to an unparsable birthdate, 
  and NPEs. I think in some cases it is reasonable to fail (user error in the
   file creation) but we need to fail gracefully and relay a helpful message 
   to the user. 