# Self-Healing Selenium Framework: HealX

## Overview

The **HealX** is a powerful Self Healing Framework designed to enhance Selenium WebDriver by adding self-healing capabilities to your automated tests. The framework automatically detects and corrects broken locators using a combination of heuristics, attribute-based recovery, and positional analysis. This ensures that your tests remain robust even as the underlying web elements change.

## Features

- **Custom WebDriver:** A wrapper around Selenium's WebDriver that adds self-healing capabilities.
- **Dynamic Locator Recovery:** Automatically attempts to heal broken locators using a prioritized list of element attributes and combinations.
- **Database Integration:** Stores element details such as attributes and positions, enabling effective self-healing.
- **First Run Mode:** Captures and stores element attributes and positions during the first execution of the test.
- **Stack Trace Analysis:** Identifies the exact locator causing an issue and attempts to heal it.

## Components

- **CustomWebDriver:** A custom WebDriver that extends the Selenium WebDriver. It attempts to heal the locator if a NoSuchElementException is thrown, utilizing the NormalRun class for healing.
- **Details:** A utility class that provides a method (getLocatorName) to retrieve the name of the locator using reflection, which helps in identifying the correct locator when an exception occurs. 
- **FirstRun:** Responsible for capturing and storing element attributes and their coordinates during the initial run. It also updates the remote database with these details.
- **FirstRunDriver:** Another custom WebDriver implementation designed to capture and store locator details in the database during the first run of the test. This helps in healing locators based on the stored data. 
- **HealX:** The core of the self-healing process. It tries to heal the broken locators using attribute-based, combination-based, and position-based healing techniques.

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven or Gradle (for dependency management)
- Selenium WebDriver
- MongoDB (or any other database for storing element data)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/Rahulgarg01/HealX.git
    cd HealX
    ```

2. Set up your database. If using MongoDB, ensure the database server is running.

3. Update the database connection details in the `RemoteDB` class.

4. Build the project using Maven or Gradle:
    ```bash
    mvn clean install
    ```

5. Include the framework as a dependency in your test project.

### Usage

1. **Initialization:**
   Create an instance of the `CustomWebDriver` or `FirstRunDriver` class by passing an existing Selenium WebDriver instance.
   ```java
   WebDriver driver = new ChromeDriver();
   CustomWebDriver customDriver = new CustomWebDriver(driver);
2. Configure your tests to capture locator details during the first run using FirstRunDriver.
3. During subsequent test runs, CustomWebDriver will automatically attempt to heal broken locators.

