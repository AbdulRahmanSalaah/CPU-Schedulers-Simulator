# Non-Preemptive and Preemptive Scheduling Algorithms

This repository contains implementations of advanced CPU scheduling algorithms, focusing on solving the starvation problem and improving the efficiency of traditional scheduling methods. The assignment is part of the **CS341 – Operating Systems 1** course at Cairo University, Faculty of Computers & Artificial Intelligence.

---

## Overview

1. **Non-Preemptive Priority Scheduling (with context switching)**
2. **Non-Preemptive Shortest Job First (SJF)**  
   *Enhanced to solve the starvation problem.*
3. **Shortest Remaining Time First (SRTF)**  
   *Enhanced to solve the starvation problem with context switching.*
4. **FCAI Scheduling Algorithm**  
   An adaptive scheduling algorithm that addresses inefficiencies and starvation in traditional methods using a composite FCAI Factor.

---

## Algorithm Details

### 1. Non-Preemptive Priority Scheduling
- Processes are scheduled based on priority.
- Context switching is implemented to simulate real CPU scheduling.
- Starvation problem is resolved by dynamically adjusting priorities.

---

### 2. Non-Preemptive Shortest Job First (SJF)
- Processes are scheduled based on the shortest burst time.
- Starvation is solved by promoting long-waiting processes to higher priority after a predefined threshold.

---

### 3. Shortest Remaining Time First (SRTF)
- Processes are scheduled based on the shortest remaining burst time.
- Starvation is solved by introducing fairness metrics to prevent long-waiting processes from being perpetually preempted.
- Context switching is implemented for preemptive execution.

---

### 4. FCAI Scheduling Algorithm
Combines priority, arrival time, and remaining burst time into a dynamic FCAI Factor for scheduling decisions.

#### FCAI Factor Formula:

Where:  
- **V1** = (last arrival time of all processes) / 10  
- **V2** = (max burst time of all processes) / 10

---

#### Quantum Allocation Rules:
- Each process starts with a unique quantum.
- If process completes its quantum and has remaining work: **Q = Q + 2**
- If process is preempted: **Q = Q + unused quantum**

---

#### Execution Rules:
- Non-preemptive execution for the first **40% of quantum**.
- Preemption is allowed after **40% execution**.

---

© 2024 Cairo University, Faculty of Computers & Artificial Intelligence

