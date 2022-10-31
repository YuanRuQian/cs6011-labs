# Lab 10 - Adventure

> Collaborators:
> - Ruqian Yuan
> - Randi Prince
> - Lauryn Hansen

## For the code to work properly
Please update your project with following files:

```shell
.
├── Game
│   └── Adventure.java
├── Heartbeat
│   └── TimerTask.java
└── Rooms
    ├── Room.java
    └── StrangeRoom.java

```

## My room: StrangeRoom 

### What's in the room

- A magical portal to any other random room ...... or it's a dead end
  - Inspired by the magical portal from Howl's Moving Castle
  - The connected doors will change every 4.2 seconds
  - If you go through some door according to an outdated door list, you might get into an unexpected room
- A locked cabinet contains an encrypted diary
  - You can unlock the cabinet with the cabinet key
  - Yet the key to the locked is too rusty. You probably can only unlock the door with it once every three times so don't give up!
  - If you get lucky the open the cabinet, you will see a diary inside.
- An encrypted diary
  - As a student with computer science background, you quickly realize that someone seems encrypted the content with Base 64 algorithm
  - No worries! There is a computer connected to the Internet in the room. So you could decode it with an online decoding tool.

### Some fun commands
```shell
// get the key to the cabinet
get Cabinet Key

// use the key to unlock the cabinet
unlock Cabinet

// pick up the diary inside the cabinet
get Diary

// read the content of the diary but it is encrypted
read Diary

// sit down by the computer, turn it on and find an online decoding tool to decode the diary message
decode Diary

// No...... You cannot pick up the computer or the cabinet
get Computer
get Cabinet
```