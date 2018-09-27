

# Constrains

## Ensure you cater that each process should not hold the license forever this should be time configurable.
  - Only in the situation, if OptimizationSolver is run into deadlock then only license will be hold forever  
   
## Number of licenses can be increased.
  - it configuration, put the entries in the SERIVCE_POOL table
  
## Cater for runtime exceptions in mid processing.
  - it is handled, license will be release in case of Error/Exception
  
## We can cluster batches of licenses example stg/dev/prod
  - I am not clear on the requirement here, not able to understand this requirement 
  
## Implementation should be able to run as a standalone package deployable into a web app or plain console java app
  - ** Few assumption about the solution ** 
  - - Currently the application is developed using the Spring Container
  - - It can be executed as stand-alone web application as it is Spring Boot Application
  - - At the moment, it can be plugged to Console Application only in case if it is developed and running using Spring Framework / Container
  - - Solution will work only if Database must be shared among all the services using Optimization Service
  
  - ** Idea of the solution **
  - - I believe, type of problem is Producer/Consumer in its nature
  - - Solution is derived based on, that, there must be a Shared Resource among the services to maintain the count
  - - At the moment solution is derived using Database, but it can be developed using a microservice as the shared resource also
