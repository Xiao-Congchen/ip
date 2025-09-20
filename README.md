# Ducky - Your Quacking Task Assistant

<img src="/src/main/resources/images/Pixel_ducky.png" alt="Ducky" width="120" />

> Ducky is a friendly desktop task management chatbot that helps you keep track of your
> **_tasks_**, **_deadlines_**, and **_events_**. <br/>
> With its intuitive command-line interface and cute pixel art style,
> managing your tasks becomes both efficient and enjoyable!

## Features

### 1. Task Management
Ducky supports three types of tasks:
- **ToDo**: Simple tasks without specific timing.
- **Deadline**: Tasks with a specific due date.
- **Event**: Tasks with start and end times.

##### 1.1 ToDo Tasks
Add simple tasks without any specific timing.

Format: `todo TASK_DESCRIPTION`

Example: `todo read book`

Expected output:
```
Quack-tastic! I've added this task to our list::
    [ ][T] read book
We now have 1 task(s).
```

##### 1.2 Deadlines
Add tasks with a specific due date (refer to the bottom for format).

Format: `deadline TASK_DESCRIPTION /by DATE_TIME`

Example: `deadline complete assignment /by 25/9/2025 2359`

Expected output:
```
Quack-tastic! I've added this task to our list::
    [D][ ] complete assignment (By: 25th September 2025, 11:59pm)
We now have 2 task(s).
```

##### 1.3 Events
Add events with start and end times.

Format: `event TASK_DESCRIPTION /from START_TIME /to END_TIME`

Example: `event team meeting /from 20/9/2025 1400 /to 20/9/2025 1600`

Expected output:
```
Quack-tastic! I've added this task to our list::
    [ ][E] team meeting (From: 20th September 2025, 2pm | To: 20th September 2025, 4pm)
We now have a total of 3 tasks.
```
<br/>

### 2. Viewing Tasks

#### 2.1 List all tasks
Shows all tasks currently being tracked.

Format: `list`

Expected output:
```
Here's your task list! Quack-quack:
    1. [ ][T] read book
    2. [ ][D] complete assignment (by: 25th September 2025, 23:59pm)
    3. [ ][E] team meeting (From: 20th September 2025, 2pm | To: 20th September 2025, 4pm)
```

#### 2.2 Find tasks
Search for tasks containing specific keywords.

Format: `find KEYWORD`

Example: `find meeting`

Expected output:
```
Quack! You are in luck!
I found some matching tasks:
    1. [ ][E] team meeting (From: 20th September 2025, 2pm | To: 20th September 2025, 4pm)
```
<br>

### 3. Updating Tasks

#### 3.1 Mark as done
Mark tasks as completed.

Format: `mark TASK_NUMBER`

Example: `mark 1`

Expected output:
```
Quack! I've marked this task as done!
  [X][T] read book
```

#### 3.2 Unmark task
Remove the completion status of a task.

Format: `unmark TASK_NUMBER`

Example: `unmark 1`

Expected output:
```
Qack! I've marked this task as not done!:
  [ ][T] read book
```
<br>

### 4. Removing Tasks

#### 4.1 Delete a specific task
Remove a single task from the list.

Format: `delete TASK_NUMBER`

Example: `delete 1`

Expected output:
```
Noms! I've gobbled up:
  [ ][T] read book
You are left with 2 task(s)!
```

#### 4.2 Clear all tasks
Remove all tasks from the list.

Format: `clearall`

Expected output:
```
I've cleared all your tasks!
Good job and keep on quacking!
```
<br>

### 5. Exiting the Program

Format: `bye`

Expected output:
```
Bye bye! See you soon!
```

## Misc - Date Time Format
When entering dates and times, please use the following formats:
- Date format: `d/M/yyyy`
- Time format: `HHmm` (24-hour format)
- Example: `25/9/2025 2359`

## Misc - Data Persistence
Your tasks are automatically **saved** after each command and will be loaded when you restart the program.

