Simple Dropwizard example to create RESTFUL web services - uses Postgres but could just as easily connect to any other data source.
execute by navigating to the root folder (where the pom.xml file is located) and typing mvn exec:java.
Alternatively import into IntelliJ or similar

You can post to create new records through doing something like this, in a shell script:
for a in `seq 501 600`; do
    echo "$a/10 to Exit."
    curl --verbose --header "Content-Type: application/json" -X POST -d '{ "id": "'"$a"'", "name":"Bar" }' http://localhost:9000/person
done

You can then query the records created by pointing your browser at http://localhost:9000/person/query/B

