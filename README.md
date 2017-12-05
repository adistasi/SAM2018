# SAM2018

An online SAM2018 Conference Paper Submission Management System built in Java 8 and Spark, a web microframework.

## Team

- Alanazi, Fasaiel
- Dalal, Niharika
- DiStasi, Andrew
- Pulle, Raseshwari



## Prerequisites

- Java 8
- Maven

## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java`
3. Open in your browser `http://localhost:4567/`
4. Access the application and browse through the tabs to explore different functionality, including submitting a paper (note that a text upload is currently in place of a file upload), viewing papers, viewing papers available for review, and requesting a paper to review.  Note that user accounts and paper submissions are saved when created.
5. Note that you must open tabs in multiple browsers if you want to have two instances running on one computer
6. Alternatively, you can access a live, hosted version of the application at 'http://sam2018.eastus.cloudapp.azure.com:4567/'

## Testing Notes
1. Predefined User Accounts (some with submitted papers) are available to make testing easier.  The following account usernames exists:
    * Admin Users: 'Admin'
    * PCC Users: 'PCC'
    * PCM Users: 'PCM1', 'PCM2', 'PCM3', & 'PCM4'
    * Submitters: 'Submitter1', 'Submitter2', 'Submitter3', 'DeniedPCM'
2. The password for all above users is 'pass'
3. When progressing through the Paper Review process, note that a Paper will not show as available for Review to a PCC User until all 3 PCM Reviews are created.

## License
MIT License.
See LICENSE for details.
