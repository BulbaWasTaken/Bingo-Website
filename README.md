[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/-cTHLqNH)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-7f7980b617ed060a017424585567c406b6ee15c891e84e1186181d67ecf80aa0.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=13998525)
# Web Server Documentation

Group: Team Z
Author1: Karl Xavier Arcilla : BulbaWasTaken
Author2: Mya Phyu : M-p1998

## Results and Conclusions

### What I Learned

Karl: With this project, I learned about writing proper response for each status code, and how response should be triggered by certain conditions like if a html file is requested but it does not exist, the server will respons with a Not Found error. I also learned about how simple 401/403 authentication work. Additionally, I learned more about how a request is structured and how a server should process it. Lastly, this project taught me how to work with files and file paths for the resources. 


Mya: I gain understanding of the HTTP protocol and handling different types of HTTP responses and requests. 
This includes learning about various HTTP status codes and headers and communicating between clients and servers.
I learned about the importance of thorough testing and debugging to ensure the correctness of software components, especially in a complex system like a web server.

### Challenges I Encountered

Karl: One of the challenge I faced was with Script Response Writer. I did not know how to process a script in Java and I never had experience doing it. I did a research and found that using Process Build and Process is the way to do this. The next hurdle after finding out I can use Process and Process builder is that I was getting an error that says my code does not know what kind of interpreter to use to run the script. At first, I hard coded node to run to process javascript and pass the test, but the problem is it does not run the .sh script in the public_html. I overcame this challenge when I noticed that the script had a comment on the first line that containts a path. I recognized that this path goes to the executable of the interpreter, so I just extracted the comment and use that to specify which interpreter to use for the Process. The only problem with our code is it does not run env scripts.


Mya: It took some time to grasp the systems and ensure that each response adhered to the HTTP protocol standards. Additionally, coordinating the interactions between different components within the web server, like requests, responses, and resources, required attention to detail. To overcome these challenges, I broke down the tasks into smaller steps and tested each component seperately and debug each method which is related to the task. I collaborated with group partner and seek clarification when needed help in resolving doubts and refining the implementation. Despite the complexities, this project provides me valuable insights into web server development.

### System Design (Extra Credit - 5 pts)

REPLACE THIS TEXT: Tell me about the design of your system. What classes did you create, and why? How do they interact? How are they organized? (A class diagram is expected to be included; you may include separate class diagrams for each package to keep the diagram readable.)
