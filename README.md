# Achieving Low-Latency Data with Edge Computing
This is the repository for the LinkedIn Learning course Achieving Low-Latency Data with Edge Computing. The full course is available from [LinkedIn Learning][lil-course-url].

![Achieving Low-Latency Data with Edge Computing][lil-thumbnail-url] 

Edge computing processes data at or near its actual physical source, which allows you to make data available faster than ever—without poor user experiences, bottlenecks, or system degradation. In this course, instructor Gregory Green walks you through how to achieve low latency by building a scaling architecture for edge computing data integration and management.

Discover best practices for reliable messaging and patterns for throughput as well as low latency with flexible data pipelines and multisite cloud-based use cases. Explore some of the most important factors that affect edge computing latency, diving deeper into antipatterns and the pros and cons of database management technologies, with examples drawn from RabbitMQ, Apache Geode, MQTT, and Spring. Along the way, Gregory gives you tips and pointers with hands-on demonstrations of how to successfully design and implement edge computing architecture for low-latency data access in real time.

## Instructions
This repository has branches for each of the videos in the course. You can use the branch pop up menu in github to switch to a specific branch and take a look at the course at that stage, or you can add `/tree/BRANCH_NAME` to the URL to go to the branch you want to access.

## Branches
The branches are structured to correspond to the videos in the course. The naming convention is `CHAPTER#_MOVIE#`. As an example, the branch named `02_03` corresponds to the second chapter and the third video in that chapter. 
Some branches will have a beginning and an end state. These are marked with the letters `b` for "beginning" and `e` for "end". The `b` branch contains the code as it is at the beginning of the movie. The `e` branch contains the code as it is at the end of the movie. The `main` branch holds the final state of the code when in the course.

When switching from one exercise files branch to the next after making changes to the files, you may get a message like this:

    error: Your local changes to the following files would be overwritten by checkout:        [files]
    Please commit your changes or stash them before you switch branches.
    Aborting

To resolve this issue:
	
    Add changes to git using this command: git add .
	Commit changes using this command: git commit -m "some message"


### Instructor

Gregory Green 
                            


                            

Check out my other courses on [LinkedIn Learning](https://www.linkedin.com/learning/instructors/gregory-green).

[lil-course-url]: https://www.linkedin.com/learning/achieving-low-latency-data-with-edge-computing
[lil-thumbnail-url]: https://cdn.lynda.com/course/2484215/2484215-1663789397814-16x9.jpg
