# stock_scraper
Script to download all trades from Netfonds.no for any specified list of paper on a given date or date range

If you want to use this script, you need to have Java 8 and Groovy installed. Also you need a file called
stocks_to_persist.txt under a folder called conf specified in the same directory where the code is checked out. This file 
must have each paper ticker listed on separate lines. Basically go to netfonds.no, search for a paper and hit enter,
then copy the ticker from the url and paste it in the config file.
So, if you want to only download all the Statoil trades for a day, put `STL.OSE` in the
file without the quotation mark. Then run the file in the command line by writing `groovy DownloadStockData` 
to get the current day, or `groovy DownloadStockData 2015-01-29` to get all the data from 2015-01-29 until today, or 
`groovy DownloadStockData 2015-01-25 2015-01-27` to get all the data between 2015-01-25 and 2015-01-27. The data will be stored
in a directory called `data`, and each paper under a directory with the ticker name. The files with the data is split up per day.
Last note: Netfonds only saves the data 2-3 weeks back in time!

This script comes with no warranty what so ever!


