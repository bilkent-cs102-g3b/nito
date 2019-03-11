# Nito
Nito is a project that is still under construction for CS102 course in Bilkent University

Nito aims to provide its users with a modern and elegant solution to
digitalize the old-fashioned tools (e.g. paper, pencil) used during
exams. Using almost all features that technology and the internet
provide us with, "Nito" strives to fill this gap as unfortunately there
is almost no application which gathers such anti-cheating services into
one software.

## Introduction
Exams have always been a stressful experience both for examinees and
examiners. While examinees worry about their results, examiners also try
to make sure that the exam is fair for everybody and there is no
cheating. After the exam is finished, grading is another painful task
for examiners because of the difficulties of reading dozens of
handwritings. The situation is even worse for programming exams: since
it is not safe to allow examinees to write their codes using computers,
examiners decide to use the old-fashioned paper/pencil method instead.
However, papers introduce new limitations in the available space and
representation of the code which leads to inconsistencies during the
grading process. Moreover, this increases the problems of examinees, as
a very small mistake in the very beginning of the code can result in the
additional effort of erasing the whole source code and having to write
it again!

This is where "Nito" (Latin word for "exam") comes in. This application
creates a secure environment for the examinees to type the answers on
their computers whilst helping examiners prevent cheating. The exams can
be created and managed very easily thanks to the features included in
"Nito". During the exam, examinees can write their solutions as if they
are using simple text editors. Furthermore, grading exam solutions
becomes as easy as reading digital texts meaning the inconsistency of
writing by hand is removed.

This tool is created to be mainly used by Computer Engineering/Science
examiners for easily creating and managing university exams, but others
(e.g. school teachers, programming courses staff, interviewers) are more
than welcome to use it in order to improve the quality of education and
to simplify other processes i.e. employment interviews.

## Details
"Nito" is designed to prevent possible digital cheating attempts during
exams. However, "Nito" can't prevent physical cheating attempts such as
cheating among two different people or use of written material. The best
results are achieved when "Nito" is being used for onsite exams while
examinees are under surveillance of assistants. That way, "Nito" can
take care of digital cheating attempts while assistants prevent physical
attempts, which leads to the safest results. On the other hand, "Nito"
can also be used globally, but the administrators need to take extra
measures for physical cheating attempts. "Nito" can't be held liable for
any physical cheating.

In order to make the exam process easier, "Nito" consists of two
different software interfaces. One of the interfaces provides facilities
for exam admins to create and configure exams, add new questions and
solution templates, invigilate examinees during the exam and grade their
solutions. The other interface serves examinees during the examination
and informs the admin about any cheating attempts. The detailed features
of these "Nito" interfaces may be found below.

## Nito - Administrator
### Exam preparation stage
- Creation and configuration of new exams (Setting up the start date and time, configuring the expected length of the exam, importing expected examinees' ID)
- Adding information pages that will be available to examinees for reading before the exam starts
- Adding questions to exams
- Supporting different types of questions such as open-response, multiple-choice, fill-in-the-blank, multiple-response, matching questions
- The possibility of setting default answers for auto-grading
- Option to set the question criteria to be used during grading
- The possibility of importing question statements
- Providing solution templates (e.g. cs101\_templates in Bilkent) for examinees
- Option for importing/exporting the exams/questions/templates
- Auto-saving all the work to the workspace chosen by the administrator
- Backup/restore/reset functionality

### During the exam
- Accessible log displaying information about new connections, cheating attempts, disconnection alerts, time notifications, and other useful info
- Timer showing the current time left until the end of the exam
- The option for extending the exam time
- Sending notification to all examinees or just the selected ones (e.g. notify about a typo in the statement or warn for cheating)
- Option to take notes for each examinee
- Ability to change the current view to one of the views below:
  **1.** Simple view:
    - Displaying the information about examinees and their status in a table (Full name, ID, Section number, IP address ...)
    - Ability to sort the data according to some column
    - Option to change to "Focused view" of the specified examinee
    - The possibility of failing or suspending an examinee attempting to cheat
    
  **2.** Monitoring view:
    - Showing real-time screen view of all connected examinees
    - Short information is shown under each view, detailed information available by right-clicking
    - Ability to resize the views
    - Option to search for some views by name or IP
    
  **3.** Focused view:
    - The detailed real-time screen view of the specified examinee
    - The ability to adjust the quality of the view if the view lags
    - Options to easily take actions (fail or suspend/block) and report it to the examinee

### After the exam, The grading process
- Ability to evaluate each solution by criteria as a checklist and assign points
- Possibility to write comments/notes for each solution
- Ability to test all solutions against plagiarism using [MOSS](https://theory.stanford.edu/~aiken/moss/) service
- Syntax highlighting for source codes in order to make the grading easier
- Ability to view the solutions of programming questions with the corrected indentation
- Option to compile and test the solutions of programming questions and see compile-time errors
- Providing export functionality to export all chosen solutions and their notes together which then can be sent to examinees for reviewing
- Printing chosen solutions with the option to include the written comments and/or criteria
- Ability to export the grades with auto-computed averages

## Nito - Examinee
### Security
The Nito - Examinee interface is a fullscreen-only software. There are a number of services this application provides in order to prevent cheating. The main services are:
- **Global keyboard listener and blocker**: Examinees will not be able to use any keyboard shortcuts such as ALT+F4, WIN, CTRL+ALT+DEL, ALT+TAB; Its aim is to prevent examinees from minimizing the software and opening other applications (e.g browser)
- **Auto screen sharing with admin**: This service provides the admin with a real-time view of the screen. With this service, detecting any cheating attempt becomes very easy as any major unexpected screen change turns out to be easily noticeable.

Moreover, there are optional minor services that can be enabled in order to improve the level of security. They are:
- **External device listener**: It is created to prevent examinees from connecting their USB devices and using them for cheating.
- **Process listener**: If the examinee manages to open some other applications, this service will directly notify the admin about the new process.

### Features
- Very simple and intuitive interface allowing to display multiple solution writing windows at the same time to make it easier to program
- A timer showing the time left
- Ability to mark questions as finished
- Auto-saving solutions to local storage and cloud service in the encrypted form. In case of failures, the ability to restore the solutions
- After exam finishes, the program automatically uploads solutions to the "Nito" - Admin.

# CS102 Spring 2018/19 &nbsp; &nbsp; &nbsp; &nbsp; Project group 3B
Instructor | David Davenport
---------- | ----------------
Assistant  | Mehmet Başaran
