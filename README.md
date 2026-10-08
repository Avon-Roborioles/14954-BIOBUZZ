# 14954 BIOBUZZ - Team Repository

This repository contains the robot code for **Team 14954 BIOBUZZ** for the 2026-2027 competition season.

---

## 🗺 Where Things You Need Live 

```
14954-BIOBUZZ/
├── TeamCode/             ← OUR code. This is the only folder you edit.
│   └── src/main/java/org/firstinspires/ftc/teamcode/
└── FtcRobotController/   ← FTC SDK. Do NOT edit. Sample OpModes live here to copy from.
```

- An **OpMode** is a program the robot runs. *TeleOp* = driver control. *Autonomous* = the robot drives itself.
- Want to start from an example? Copy a sample from `FtcRobotController/.../external/samples` into `TeamCode`, then change **the copy**.
- **Do not use OnBot Java or Blocks.** They save code on the robot, not in git, so your work would be lost.

---

## 🚦 Git Workflow

### 1. The Branches
*   **`master`**: **The Robot Branch.** Only stable, competition-ready code. This is what we run at matches. **Never edit this directly.**
*   **`dev`**: **The Workbench.** This is where the team works daily. **Everyone works here for now.**
*   **Feature Branches (`feature/task-name`)**: **The Sandbox.** Temporary branches for bigger tasks. *We are not using these yet.*

### 2. The Golden Rules
1.  **PULL** every time you open Android Studio.
2.  **CHECK** that you are on `dev` before you change anything.
3.  **COMMIT** often with clear messages (e.g., "Tuned intake motor power").
4.  **PUSH** before you leave, so your work is saved on GitHub.
5.  **STUCK? STOP.** If you see something scary (merge conflict, red error, wrong branch), do not click around. Ask the lead.

### 3. Your Daily Loop

| Step            | What to do in Android Studio                                                                                                        |
|-----------------|-------------------------------------------------------------------------------------------------------------------------------------|
| 1. Pull         | **Git menu → Pull**                                                                                                                 |
| 2. Check branch | Look at the branch name in the toolbar. It must say `dev`.                                                                          |
| 3. Code         | Edit **your own files** (see the table below).                                                                                      |
| 4. Commit       | Open the **Commit** panel on the left. Tick your files, write a message, click **Commit**. (Shortcut: `Ctrl+K`, or `Cmd+K` on Mac.) |
| 5. Push         | **Git menu → Push**. (Shortcut: `Ctrl+Shift+K`, or `Cmd+Shift+K` on Mac.)                                                           |
| 6. Check        | Open the repo on GitHub and make sure your commit is there.                                                                         |

### 4. Who Owns What
To avoid two people editing the same file, each file has one owner. Want to change someone else's file? Ask them first.

| File            | Owner |
|-----------------|-------|
| `Drive.java`    | *TBD* |
| `Intake.java`   | *TBD* |
| `Launcher.java` | *TBD* |
| `TeleOp.java`   | *TBD* |
| `Auto.java`     | *TBD* |

### 5. Key Terms
*   **Repository (Repo)**: This project, stored on GitHub.
*   **Clone**: Download the repo to your laptop for the first time.
*   **Branch**: A separate line of work inside the repo (`master`, `dev`).
*   **Commit**: A "Save Point" stored on your laptop.
*   **Push**: Send your commits from your laptop to GitHub.
*   **Pull**: Get everyone else's latest commits from GitHub onto your laptop.
*   **Merge**: Combine work from one branch into another.
*   **Merge Conflict**: Git cannot tell which version of a line to keep because two people changed it. **Stop and ask the lead.**
*   **Deploy**: Send the code from your laptop to the robot (the Run button).

---

## 🤖 Robot Rules

1.  **Communicate before you deploy.** Make sure everyone knows the current master commit. The robot runs whatever was deployed last, so you could overwrite a teammate's test.
2.  **One person on the robot at a time.**
3.  **Before every competition,** tag the exact commit that goes on the robot, so we can always get back to it.

---

## 🛠 Getting Started

### Before you begin
*   Android Studio is installed.
*   You have a GitHub account and have accepted the invite to the team repo.

### Setup
1.  Open Android Studio. On the welcome screen click **Get from VCS**. (If a project is already open: **File → New → Project from Version Control**.)
2.  Paste this URL: `https://github.com/Avon-Roborioles/14954-BIOBUZZ.git`
3.  Pick a folder on your laptop and click **Clone**.
4.  Wait for **Gradle sync** to finish. The first time can take several minutes. Do not close Android Studio.
5.  Switch to `dev`: click the branch name in the toolbar, choose `origin/dev`, then **Checkout**.
6.  Start coding in the `TeamCode` folder!

If Android Studio asks for your name and email when you commit, use your real name and the email on your GitHub account.

---

## 🤖 AI Policy

Generative AI tools (e.g., ChatGPT, GitHub Copilot, Gemini) are **strictly prohibited**. All code in this repository must be written and debugged directly by team members so everyone learns and understands how the robot works.
---

## FTC SDK Information (Original)

This repository is based on the official FTC SDK.

### User Documentation and Tutorials
*FIRST* maintains online documentation with information and tutorials on how to use the *FIRST* Tech Challenge software and robot control system. You can access it here:

[FIRST Tech Challenge Documentation](https://ftc-docs.firstinspires.org/index.html)

### Javadoc Reference Material
The Javadoc reference documentation for the FTC SDK is available online:

[FTC Javadoc Documentation](https://javadoc.io/doc/org.firstinspires.ftc)

### Sample OpModes
Samples are located in: [`/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples`](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples)