# Welcome to the Programming Team

> **Last reviewed:** 2026-11 (BIOBUZZ season) · **Maintainer:** current programming lead
>
>This guide is meant to last longer than any one season. If something in it is out of date, fix it. See [Keeping this doc alive](#9-keeping-this-doc-alive).

## 1. Start here

If you're new and you have no idea what's going on, that's normal. I wrote this guide because my first weeks here I didn't know what to do, who to ask, or how the code worked. Nobody should have to go through that.

**What we do:** We write the code that makes the robot move, grab, and score. We do it in Java, in Android Studio, and we keep it all on GitHub.

**Your first-week goal is small:** make **one safe commit**. That's it. By the end of week one you should be able to:

- Open the project in Android Studio
- Clone the team repo from GitHub
- Know who your partner is and which robot section you're on
- Read the tasks file and pick a starter task
- Push one small change to GitHub

**Who to ask:** your partner first, then the programming lead. See [When you're stuck](#7-when-youre-stuck).

## 2. How we work

We learned the hard way that telling everyone to learn to code on their own doesn't work. Here's what does.

**Sections**

We split the robot into sections. For example: drive, input/passthrough, launcher. Each section has its own list of jobs and the sensors needed to do them.

**Pairs**

Every section is owned by two people working as a pair. You don't code alone, and you don't get lost alone.

**The tasks file**

The tasks file in the repo (`tasks.md`) is our shared to-do list. It has:

- a Teams table: who is in which section
- a Current tasks table: every job, its owner, its team, and its status

**Statuses, and what they really mean:**

| Status | Meaning |
|---|---|
| `todo` | Not started |
| `doing` | Someone is working on it right now |
| `needs check` | The code is written, but not yet tested on the real robot |
| `done` | Tested and works on the robot |
| `blocked` | Stuck, needs help |

Notice that `done` means it works on the robot, not "I finished typing." Code that hasn't touched the robot isn't done.

**The reason we do it this way:** Everyone can see what needs doing, nobody has to guess, and `blocked` gives you an official way to say "I need help" without feeling bad about it.

**Check-ins**

Every third meeting, we stop and talk for a few minutes: what moved, what's stuck, what's next. Come with your status updated.

## 3. How we learn

On a lot of teams, the people who already know how to code end up writing most of the code, and newer members just watch. We don't want that here. These norms apply to everyone, veterans included.

1. **Veterans explain, newer members type.** If you already know how something works, your job is to help your partner understand it, not to do it for them. Because you only really learn by typing it yourself and fixing your own mistakes.
2. **Partners swap.** Take turns being the one at the keyboard each session. Because watching someone else code feels like learning, but it isn't.
3. **Nobody merges code they can't explain.** If you can't say what a piece of code does, it doesn't go in until you can. Because mystery code is how robots break at competitions.
4. **Asking is expected.** Questions are how the team gets better. Nobody gets judged for asking. Because the team where only a few people understand the code is one graduation away from disaster.
5. **Explain it back.** When you finish a task, be ready to explain to your partner or the lead what you wrote and why. Because if you can teach it, you own it.

## 4. The learning ladder

Work through these in order. Each step has a "you're ready to move on when" line, so you know when to stop and go to the next one. You don't need to be an expert at any step, just able to do what's listed.

**Step 1: Java basics**

Variables, `if` statements, loops, methods, and classes.

**Ready when:** you can read a short method and explain what it does, and write a small one that uses an `if` and a loop.

Resources: [add link to team training session / slides]

**Step 2: Git and GitHub**

What a repository, commit, branch, push, and pull are, and how to use them without being scared of breaking things.

**Ready when:** you can clone the repo, make a change, commit it, push it, and pull someone else's changes.

Resources: [add link to git training session]. The README in the repo explains our branch strategy.

**Step 3: Android Studio**

How to open the project, build it, and connect to the robot.

**Ready when:** you can build the project and connect to the robot's controller without help.

**Step 4: FTC robot code**

How the robot's code is structured: OpModes, the `hardwareMap`, motors, servos, and reading the gamepad. Also the idea of a loop that runs again and again while the robot is on.

**Ready when:** you can write a tiny OpMode that spins a motor when you press a button on the gamepad.

Resources: [add link to FTC code training session]

**Step 5: Sensors**

How the robot sees and feels: color sensors, cameras like Limelight, encoders, and so on. Read a value, print it to the screen, then use it to make a decision.

**Ready when:** you can read one sensor value and show it on the driver station.

**Step 6: Read the codebase**

Use the [codebase tour](#5-codebase-tour) to find where your section's code lives and how it connects.

**Ready when:** you can point to the file where your section's main code lives and explain how a button press ends up as motor movement.

**Step 7: Your first task**

Pick a starter task. See [Your first task](#6-your-first-task).

You can work on Step 7 while still learning Steps 4 to 6. Pairing with a partner means you learn by doing.

## 5. Codebase tour

Every season we start a fresh repo, so this section teaches you how to read any season's code, not one specific layout.

**The big picture**

FTC projects usually have two main parts:

- The robot controller app is already built by FIRST. Leave it alone.
- The `TeamCode` folder: this is our code. Almost everything we write lives here.

**How a button press becomes motion**

1. A driver presses a button on the gamepad.
2. Our OpMode, the main program, runs in a loop and checks the gamepad.
3. The code decides what to do and sends a command, like set this motor to this power.
4. The `hardwareMap` is how the code finds the real motor, servo, or sensor by name.
5. The robot moves.

If something doesn't work, check each link in that chain.

**Where do I find my section's code?**

In general:

- Drive code: anything about moving the robot around, wheels, driving, path following.
- Intake code: anything about picking things up and moving them inside the robot.
- Launcher code: anything about aiming and shooting.

Look for file and folder names that match the section.

**This season's map**

Update this each season. List the real folders and what's in them.

```
[folder / file] -> [what it does]
```

## 6. Your first task

The tasks file has a list of real jobs. For your first one, pick something small and low-risk.

**A good starter task:**

- touches one motor, one servo, or one sensor
- can be tested quickly on the robot
- is small enough to finish in a session or two with your partner

**Process:**

1. Tell your partner which task you're taking.
2. Put your name in the Owner column and set the status to `doing`.
3. Write the code together: the newer person types.
4. Commit and push your work.
5. Set the status to `needs check`.
6. Test it on the real robot. If it works, set it to `done`. If it doesn't, ask for help or mark it `blocked`.

Starter tasks live in this season's tasks file. [Link them here.]

## 7. When you're stuck

Being stuck is part of the job. Here's the order to try things in:

1. Try on your own for about 20 minutes. Read the error message, check your last change, search the error.
2. Ask your partner.
3. Ask the programming lead.
4. Post in the team chat: [add the channel your team uses]
5. Mark the task `blocked` in the tasks file so everyone knows you need help.

When you ask, include: what you were trying to do, what happened instead, and what you already tried. It gets you answers faster.

## 8. Glossary

| Term | Plain-English meaning |
|---|---|
| Android Studio | The program we write and build the robot code in |
| Branch | A separate copy of the code where you can make changes without affecting the main version |
| Commit | Saving a snapshot of your changes with a short message |
| Driver Station | The device the drivers use to control the robot |
| Encoder | A sensor that measures how far or how fast a motor has turned |
| Gamepad | The controller the driver holds |
| GitHub | The website where our team's code is stored and shared |
| hardwareMap | How our code finds the real motors, servos, and sensors by name |
| Limelight | A camera used for vision |
| Merge | Combining one branch's changes into another |
| OpMode | A single robot program, like drive mode or autonomous |
| Pull | Downloading the latest changes from GitHub |
| Pull request | A request to add your changes to a branch, so others can look first |
| Push | Uploading your commits to GitHub |
| Repository (repo) | The folder of code, tracked by Git, that lives on GitHub |
| Servo | A motor that turns to a specific position |
| TeamCode | The folder where our team's code lives |
| TeleOp | The driver-controlled part of a match |
| Autonomous | The part of the match where the robot runs on its own |

## 9. Keeping this doc alive

This guide only helps if it stays current.

- At the start of every season, the programming lead reviews the whole guide and updates the Last reviewed line at the top.
- Update the codebase tour map and the starter tasks link for the new repo.
- Add links to any new training sessions in the learning ladder.
- When a new member says this part confused me, fix that part. They're telling you what's missing.
- Before you graduate, hand this guide to the next programming lead and walk them through this section.

Each season gets a new repo, so make sure this guide doesn't get left behind. Link to it from each season's README, and add check the onboarding guide to your start-of-season checklist.
