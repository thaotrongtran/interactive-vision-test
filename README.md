## Inspiration
Half of our team has bad eyesight, so we thought it would be a cool idea if individuals could track their eyesight improvements or degradation on their own.

## What it does
Our project tests the user's eyesight using the standard font sizes used by the Snellen eye chart. For best use, stand two feet away from the screen and remove glasses or contacts. Running the java file and using Amazon Alexa in coordination, the user can run the eye test program and request Alexa for a "Vision Test." At this point, a character will appear on the screen. The user must guess "Is it (insert the alphabetic character of your choosing)?" This response will be sent to a database in Azure, and the java program will access the results. If the answer is correct, the test continues with a smaller font. If the answer is incorrect, the font gets larger and the user's incorrect answer count goes up. Once the user gets 3 guesses wrong, the test will end, and the user's results will be printed.

## How we built it
The backbone of our program was a java program that controlled logical operations and assignments. The Java program retrieves information from an Azure database which stored user input. This input was gathered using Lambda function in an Alexa skill; the input was then sent to the Azure SQL database for queries.

## Challenges we ran into
The connections we had to make between AWS and Azure, and then Azure and the Eclipse IDE that we used to create our Java program, were the most difficult aspects of this project. We ran into a plethora of errors from simple variable name mismatches to problems with TCP/IP.

## Accomplishments that we're proud of
We're proud of our final result. We really struggled in the last leg, trying to get Azure and Java to work correctly together, but once we finally got it to work, we were so happy!

## What we learned
We each learned something different, and a lot of that information we learned from each other. One of our members learned how to create and manipulate GUIs in Java, two of our members learned node.js and how to use AWS, and another of our members learned how to use Azure.

## What's next for Interactive Vision Test
Because none of us are optometrists, our information may not be 100% accurate. We researched thoroughly, but we can't be sure all calculations are correct. The next step would be to have a professional optometrist judge our product and give feedback.
