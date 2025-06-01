# ADWEF-CLOUD: Adaptable Decentralized Workflow Execution Framework in Cloud

This repository provides the source code and experimental setup for **ADWEF@Cloud**, a framework for **adaptive decentralized workflow execution in cloud computing**, introduced by:

📄 **Published Article**:  
Safi-Esfahani, F., & Khatibi, N. (2025). *Adaptable decentralized workflow execution with fuzzy framework in cloud computing (ADWEF.Cloud)*. Computing, Springer.  
[Link to paper](https://link.springer.com/article/10.1007/s00607-025-01480-5)

---

## 🔍 Overview

Traditional centralized workflow engines suffer from:
- Bottlenecks  
- Single points of failure  
- Poor scalability in distributed cloud settings  

ADWEF.Cloud addresses these challenges by:
- Executing **decentralized workflows** across virtual machines (VMs)
- Dynamically adapting **workflow fragmentation** to runtime conditions
- Using **fuzzy logic** to manage **fragment-proportionality** and **available-bandwidth**

---

## ✨ Key Features

- **ADWEF Framework**: Runtime-adaptable orchestration layer
- **Fragment-Proportionality**: Number of workflow fragments aligned with available VMs
- **Bandwidth Adaptability**: Fragmentation responds to network communication capacity
- **Fuzzy Decision-Making (FDDM)**: Intelligent rule-based selection of fragmentation strategy
- **Improved Performance**:
  - Up to **97% improvement** in response time
  - Up to **306% boost** in throughput
  - Up to **93% reduction** in message exchange volume

---

## 🧠 Background

This work builds upon:

- 📰 **JSS 2011**: [Adaptable Decentralized SOA](https://doi.org/10.1016/j.jss.2011.03.031)
- 🎓 **PhD Thesis (UPM 2011)**: *Adaptable Decentralized Orchestration Engine for Block-Structured Workflows in SOA* availabe in the thesis folder of the repo!
---

## 📊 Experimental Setup

- **Platform**: CloudSim-based simulation
- **Workflows**: Reference loan application process (BPEL, block-structured)
- **Metrics**: Response time, throughput, bandwidth usage, exchanged messages
- **Conditions**:
  - Variable number of VMs
  - Variable and constant bandwidth scenarios
  - Comparison against fully decentralized (FPD) and HPD baselines

---

## 🛠️ Getting Started

1. Clone this repository  
   ```bash
   git clone https://github.com/faramarzsafi/ADWEF-CLOUD.git
   cd ADWEF-CLOUD
   ```

2. Set up dependencies  
   ```bash
   pip install -r requirements.txt
   ```

3. Run an example simulation  
   ```bash
   python experiments/run_adwef_simulation.py --config configs/sample_config.json
   ```

---

## 📜 Citation

Please cite this work as:

```bibtex
@article{safi2025adwefcloud,
  title={Adaptable decentralized workflow execution with fuzzy framework in cloud computing (ADWEF.Cloud)},
  author={Safi-Esfahani, Faramarz and Khatibi, Narges},
  journal={Computing},
  year={2025},
  volume={107},
  number={133},
  publisher={Springer},
  doi={10.1007/s00607-025-01480-5}
}
```

---

## 👨‍👩‍👧‍👦 ADWEF Family of Research

- [Springer 2025 – ADWEF.Cloud Paper](https://link.springer.com/article/10.1007/s00607-025-01480-5)
- [JSS 2011 – Adaptable Decentralized SOA](https://doi.org/10.1016/j.jss.2011.03.031)
- [PhD Thesis – UPM 2011]
- [ATSDS: Adaptive Two-Stage Deadline-Constrained Scheduling](https://link.springer.com/article/10.1007/s11227-016-1928-z)  
  - Proposes runtime-aware deadline-sensitive scheduling for workflows in cloud environments.
- [Taxonomy of Workflow Partitioning Methods](https://www.sciencedirect.com/science/article/pii/S0164121217300821)  
  - Presents a comprehensive classification of workflow partitioning techniques used in distributed systems.

---
