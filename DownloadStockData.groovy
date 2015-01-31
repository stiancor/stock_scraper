import java.time.LocalDate
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

def createDirectoryIfNotExists(dir) {
    def folder = new File(dir)
    if (!folder.exists())
        folder.mkdirs()
}

createDirectoryIfNotExists('conf')
createDirectoryIfNotExists('data')

// Add a next() method
LocalDate.metaClass.next = { delegate.plusDays(1) }

fromDate = LocalDate.now()
toDate = LocalDate.now()

def parseToDateIfValid(String arg) {
    LocalDate.parse(arg)
}

def verifyArgs(args) {
    if (args.size() > 2) throw new IllegalArgumentException('There cannot be more than two arguments')
    if (args.size() == 2) {
        toDate = parseToDateIfValid(args[1] as String)
    }
    fromDate = parseToDateIfValid(args[0] as String)
}

if (args.size() > 0) {
    verifyArgs(args)
}

def f(date) {
    date.format(DateTimeFormatter.BASIC_ISO_DATE)
}

def getDirectoryName(paper) {
    "data/${paper}"
}

def getFileName(paper, date) {
    "${f(date)}_${paper}_tradedump.txt"
}

config = new File('conf/stocks_to_persist.txt').getText('UTF-8')

config.eachLine { paper ->
    println "Fetching data from ${fromDate} until ${toDate} for ${paper}"
    createDirectoryIfNotExists(getDirectoryName(paper))
    (fromDate..toDate).each { LocalDate date ->
        if(date.getDayOfWeek().getValue() < 6) {            
            println "Processing [ Paper: ${paper}, Type: tradedump, Date: ${date}, Week day: ${date.getDayOfWeek()} ]"
            def filePath = getDirectoryName(paper) + '/' + getFileName(paper, date)
            if (!new File(filePath).exists()) {
                def content = "http://www.netfonds.no/quotes/posdump.php?date=${f(date)}&paper=${paper}&csv_format=csv".toURL().text
                new File(filePath).write(content)
            } else
                println "File ${filePath} already exists. Skipping!"
        } 
    }
}
