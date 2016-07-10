
#!/bin/bash

clear;
# bash trap function is executed when CTRL-C is pressed:
# bash prints message => Executing bash trap subrutine !

# for loop from 1/10 to 10/10
for a in `seq 501 600`; do
    echo "$a/10 to Exit."
    curl --verbose --header "Content-Type: application/json" -X POST -d '{ "id": "'"$a"'", "name":"Bar" }' http://localhost:9000/person

done
echo "Work Complete!!!"
