# FullMarksBot User Guide

Welcome to **FullMarksBot** – your personal assistant for managing tasks, deadlines, and events!

FullMarksBot helps you organize your todos, deadlines, and events. You can add, list, mark, unmark, delete, and find tasks easily. All tasks are saved automatically and loaded when you start the bot.

## Adding Tasks

To add a task, enter your task description and specify it as either a ToDo, Deadline, or Event when prompted. Provide the needed dates in the format `yyyy-MM-ddTHH:mm` (e.g., `2025-09-30T23:59`).

**Example:**
```
User: Submit assignment
FullMarksBot: Is this a Todo, Deadline or Event task?
User: deadline
FullMarksBot: When should it be done by? (yyyy-MM-ddTHH:mm):
2025-09-30T23:59
New Deadline: New Deadline: Submit assignment
```

## Listing Tasks

Show all tasks in your list.

**Command:**
```
list
```

**Expected Output:**
```
1: [T][ ] Buy groceries
2: [D][X] Submit assignment (by: Sep 30 2025, 11:59 PM)
```

## Marking/Unmarking Tasks

Mark a task as done or undone.

**Commands:**
```
mark <task number>
unmark <task number>
```

**Expected Output:**
```
mark 1
Congrats! You completed this task!
unmark 1
Oh no! Let me unmark this...
```

## Deleting Tasks

Remove a task from your list.

**Command:**
```
delete <task number>
```

**Expected Output:**
```
delete 2
Let's get this task out of here.
```

## Finding Tasks

Search for tasks containing a specific keyword.

**Command:**
```
find <keyword>
```

**Expected Output:**
```
find groceries
Here are the related tasks:
     1.[T][ ] Buy groceries
```

## Exiting

Say bye and exit the bot.

**Command:**
```
bye
```

**Expected Output:**
```
bye bye for now!
```

## Notes

- Dates must be entered in the format `yyyy-MM-ddTHH:mm` (e.g., `2025-09-30T23:59`).
- Task numbers start from 1 as shown in the list.
- All tasks are saved automatically and loaded when you start FullMarksBot.

## Troubleshooting

- If you encounter errors, check that your input matches the expected format.
- For date-related tasks, ensure the date format is correct.

