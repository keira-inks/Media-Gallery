# CSCI 1302 - Gallery v2025.sp

This is the description of the Gallery
project provided by the Fall 2024 CSCI 1302 classes
at the University of Georgia. Some things have been 
removed from this README.md file, as it is not relevant
to the understanding of the project.

## Table of Contents

* [Learning Outcomes](#course-specific-learning-outcomes)
* [Project Description](#project-description)
* [Suggested Checklist](#suggested-checklist)
* [Project Requirements & Grading](#project-requirements--grading)
  * [Functional Requirements](#functional-requirements)
  * [Non-Functional Requirements](#non-functional-requirements)
  * [Absolute Requirements](#absolute-requirements)

## Course-Specific Learning Outcomes

* **LO1.d:** Use shell commands to compile new and existing software solutions that
are organized into multi-level packages and have external dependencies.
* **LO2.e:** Utilize existing generic methods, interfaces, and classes in a software solution.
* **LO3.a:** Create and update source code that adheres to established style guidelines.
* **LO3.b:** Create class, interface, method, and inline documentation that satisfies a set of requirements.
* **LO5.b:** Utilize a build tool such as Maven or Ant to create and manage a complex software solution involving external dependencies.
* **LO7.a:** Design and implement a graphical user interface in a software project.
* **LO7.c:** Use common abstract data types and structures, including lists, queues, arrays, and stacks in solving typical problems.

## Project Description

Your goal is to implement a GUI application in Java using JavaFX 17 that displays a
gallery of images based on the results of a search query to the
[iTunes Search API](https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html).
This will require you to look up things in Javadoc and apply your knowledge of
things like inheritance, polymorphism, and interfaces. The functional
and non-functional requirements for this project are outlined later in this
document. Here is an example of what your program might look like:

[![screenshot1](https://raw.githubusercontent.com/cs1302uga/cs1302-gallery/master/resources/screenshot.png)](https://youtu.be/5SsO63m-Q5A)

Click the image above or [here](https://youtu.be/5SsO63m-Q5A) for a video demo of the app (no audio).

Part of software development is being given a goal but not necessarily being
given instruction on all of the details needed to accomplish that goal. Starter code and 
a generously helpful [FAQ](#faq)
are provided. After actively reading through the main parts of this project description
and taking notes, please read through the [FAQ](#faq) to see if it answers any of your
questions.

This project is also designed to help you better understand the usefulness of good
class design. While you can technically write your entire JavaFX-based
GUI application entirely in the `start` method, this will make your code messy,
hard to read, possibly redundant, likely more prone to errors, and it wouldn't pass
a style audit. Before you write any code, you should plan out your application's
scene graph (i.e., the containment hierarchy), and design custom components as needed.
If you find that you are writing a lot of code related to a specific component
(e.g., setting styling, adding event handlers, etc.), then it's probably
a good idea to make a custom version of that component in order to reduce
clutter.

We are also using this project to further introduce you to
[Maven](https://maven.apache.org/what-is-maven.html).
As you are actively encouraged to create and document additional classes in order
to create customized, reusable components (e.g., for dealing with the image views),
it would be slightly tedious to compile and run your code frequently.
Instead, you can use Maven to more easily compile your code and handle dependencies
(e.g., for the Gson library mentioned in the FAQ). Please see the
"Project Structure" sub-section of the
[Absolute Requirements](#absolute-requirements) section for an overview of
what you can do with Maven. While this project is already provided to you
as a Maven project, you may still find is useful to read the
[CSCI 1302 Maven Chapter](https://cs1302uga.github.io/cs1302-book/tools/maven/maven-index.html)
to gain a better understanding of what Maven is.

## Suggested Checklist

To help you with planning out this project, here are some suggested steps you can take that
your instructors believe will help you complete the project more easily. Some of the
items in this checklist may not make sense until you have read the entire project description,
including the appendices. These steps are suggestions and, therefore, do not constitute an
exhaustive list of steps that you may need to take to complete the project.

**Preparation:**

- [ ] Read through the entire project description, including the appendices,
      **and write down questions as you go.**
- [ ] Read it again! This time, you may be able to answer some of your own
      questions.

**Frontend:**

- [ ] On paper, decompose the visual elements of your application to identify
      some of the objects you may need.
- [ ] On paper, draw the scene graph for your app.
- [ ] Identify opportunities for the creation of custom components.
- [ ] Write some of the code to make your app look like what it needs to look like.
- [ ] Use Git to stage and commit your changes often; create and merge branches,
      as needed.

**Backend:**

- [ ] Create any custom component classes.
- [ ] On paper, hash out how you might store the list or lists of image URIs.
- [ ] Write a method to retrieve the JSON response string for a query to the iTunes
      Search API based on a query string.
- [ ] Write a method that returns a list of URI strings based on a JSON response
      string retrieved from the iTunes Search API.
- [ ] Use Git to stage and commit your changes often; create and merge branches,
      as needed.

**Putting it all Together:**

- [ ] Consider using a list or array to help you access the various `ImageView`
      objects in your scene; making new `ImageView` objects all the time is
      _not_ recommended. Remember, the `ImageView` objects are like the picture
      frames. You wouldn't toss out a picture frame every time you changed the picture.
- [ ] Make the buttons work at a basic level (you can decide what basic is).
- [ ] Make the progress bar work (requires careful consideration of the threading
      required by some of your app's event handlers).
- [ ] Make the random replacement work.
- [ ] Use Git to stage and commit your changes often; create and merge branches,
      as needed.

## Project Requirements & Grading

This assignment is worth 100 points. The lowest possible grade is 0, and the
highest possible grade is 110 (due to extra credit).

### Functional Requirements

* **Main Requirements (100 points):** Your application needs to have the components
  listed below. They need to function as described. If a certain aspect of a component
  (e.g., style or behavior) is not specified in these requirements, then that
  aspect is at the discretion of the implementor. The screenshot provided earlier
  in this project description is meant to serve as a reference.
  **It is okay if implementors deviate visually from the screenshot.** You should
  set up the application's scene graph in whatever way makes most sense to you and meets
  the requirements. In addition to the required components, implementors should feel
  free to add more components and/or functionality as long they do not distract too heavily
  from the functionality of the required components. Here are the required components:

  Before we break down the points, please see the mockup below, which contain notes
  about the app's required functionality. **Details provided in the mockup are to be
  considered as part of the project requirements.** To access the mockup, you can click on the
  image below or the link below the image.

  <p align="center">
  <a href="https://www.figma.com/file/EGF7oPEpbLIdlQIrXsPRD7/cs1302-gallery?node-id=0%3A1"><img src="https://raw.githubusercontent.com/cs1302uga/cs1302-gallery/master/resources/mockup_thumb.png"></a>
  <br>
  <a href="https://www.figma.com/file/EGF7oPEpbLIdlQIrXsPRD7/cs1302-gallery?node-id=0%3A1">Entire Mockup</a> | <a href="https://www.figma.com/proto/EGF7oPEpbLIdlQIrXsPRD7/cs1302-gallery?page-id=0%3A1&node-id=1%3A3&viewport=241%2C48%2C1.08&scaling=min-zoom&starting-point-node-id=1%3A3&show-proto-sidebar=1">Mockup Slideshow</a>
  </p>

  Here are some details about the required elements:

  * **Search Bar (60 points):** The app  must have an area near the top that contains
    a play/pause button, a component for users to enter a search term, a component
    for users to select a media type, and a "Get Images" button. Additional
    details are provided in the mockup.

    * *Play/Pause Button (10 points):* This button should allow the user to
      enter and leave "play" mode, the semantics of which are described
      later under "Random Replacement." The button text should change, as needed,
      to reflect whether the app is "play" mode.

    * *Query Term Field (10 points):* This component should allow the user to
      enter in a **term** that your application will use when it queries the
	  iTunes Search API. Its initial contents should correspond to some default term
	  of your choosing (e.g., `"jack johnson"`).

    * *Query Media Type Dropdown (10 points):* This component should allow the user to
      select a **media** type that your application will use when it queries the
	  iTunes Search API. This dropdown should default to having "music" selected.

    * *Get Images Button (30 points):* This button should cause the application
      to query the iTunes Search API using the URL-encoded version of the term
      and media type provided by the user, then update the "Main Content" area
      accordingly and/or show an alert (if a problem is encountered).

	  * Instructions for how to query the iTunes Search API are provided
	    [here](#query-how), including a description of the parameters
		you can provide when performing a query. When your application performs
		a search query, **only the following parameters should be used:**

        | Parameter | Value                                                           |
        |-----------|-----------------------------------------------------------------|
        | `term`    | URL-encoded version of the **term** provided by the user.       |
        | `media`   | URL-encoded version of the **media** type provided by the user. |
        | `limit`   | `"200"`                                                         |

      * Only a distinct set of URIs should used if there
        are any duplicates. Implementers are not expected to handle situations
        where two distinct URIs refer to identical images.

      * If **less than twenty-one (21)** distinct artwork image URIs are available
	    in the query response, then an alert dialog should be displayed to the
		user with an appropriate error message. In this scenario, the images in
		the main content area should not be updated.

      * If **twenty-one (21) or more** distinct artwork image URIs are available in
	    the query response, then all the images associated with those distinct URIs
		should be downloaded. After all the downloads are complete, the main content
		area should be updated to display the first 20 downloaded images. The
        remaining images should not be omitted as they will be needed to facilitate
        the "random replacement" described elsewhere in this document.

  * **Message Bar (10):** The app must have an area near the top that
    provides initial instructions to a user, then gets updated over time,
    as the user interacts with the app. Additional details are provided in the
    mockup.

  * **Main Content (10):** The app must have an area near its center that displays
    a collection of twenty (20) artwork images. The initial set of images are all
    the same default image (`"file:resources/default.png"`), but users can update
    the images by clicking the "Get Images" button. Additional details are provided in the
    mockup.

    * Images are gathered by querying the iTunes Search API. We have included
      information [here](#query-how) on how to programmatically perform such
      a query. A query response may contain multiple results, each with its own
      artwork URI (named `artworkUrl100`) that you can use to download the images.

  * **Status Bar (10)** The app must have an area near the bottom that contains
    a functional progress bar and a message indicating that images are provided
    by the iTunes Search API. Additional details are provided in the mockup.

    * *Progress Bar (10 points):* The application needs to have a progress bar
      that indicates the progress of downloading all the images once a distinct
      set of image URIs is determined for a query response. Please note that
      **this progress bar is not merely aesthetic**. It should actually show the
      progress of downloading the individual images.

  * **Random Replacement (10):** The app must allow users to enter into a "play" mode
    after images are successfully gathered. While in play mode, the app randomly
    replaces one of the displayed images with an already downloaded image. Users
    should be able to turn off "play" mode once they start it, either by clicking
    "Pause" (changes the button to "Play" and stops the replacements) or by
    clicking the "Update Images" button. Whenever the play/pause button displays
    "Play" is enabled, a user should be able to click the play/pause button
    to enter "play" mode. Additional details are provided in the mockup.

### Non-Functional Requirements

A non-functional requirement is *subtracted* from your point total if
not satisfied. To emphasize the importance of these requirements,
non-compliance results in the full point amount being subtracted from your
point total. That is, they are all or nothing.

* **(0 points) [RECOMMENDED] No Static Variables:** Use of static variables
  is not appropriate for this assignment. However, static constants
  and static utility and/or test methods are perfectly fine.

* **(10 points) User-Friendly Experience:**
  The windows of your application should not exceed a pixel dimension of 1280 (width) by 720 (height).
  Except for reasonable delays caused by X-forwarding, your application should not
  hang/freeze or crash during execution. If a grader encounters lag, they will
  try to run your application locally using the same version of Java/JavaFX used on Odin.

* **(20 points) Code Style Guidelines:** You should be consistent with the style
  aspect of your code to promote readability. Every `.java` file that
  you include as part of your submission for this project must be in valid style
  as defined in the [CS1302 Code Style Guide](https://github.com/cs1302uga/cs1302-styleguide).
  All of the individual code style guidelines listed in that document are part
  of this single non-functional requirement. Like the other non-functional
  requirements, this requirement is all or nothing.

  **NOTE:** The [CS1302 Code Style Guide](https://github.com/cs1302uga/cs1302-styleguide)
  includes instructions on how to use the `check1302` program to check
  your code for compliance on Odin.

* **In-line Documentation (10 points):** Code blocks should be adequately documented
  using in-line comments. This is especially necessary when a block of code
  is not immediately understood by a reader (e.g., yourself or the grader).

### Absolute Requirements

An absolute requirement is similar to a non-functional requirement, except that violating
it will result in an immediate zero for the assignment. In many cases, a violation
will prevent the graders from evaluating your functional requirements. No attempts will be
made to modify your submission to evaluate other requirements.

* **Project Structure:** The location of the default
  package for the source code should be a direct subdirectory called `src/main/java`.
  When the project is compiled using Maven, the default package for compiled
  code should be `target/classes`. The classes in the starter code are in
  the `cs1302.gallery` package. **Any additional classes that you create should
  be located in or under the `cs1302.gallery` package.**
  The main application class should be `cs1302.gallery.GalleryApp`, and the
  driver class should be `cs1302.gallery.GalleryDriver`.

  Executable [scripts](https://cs1302uga.github.io/cs1302-book/tools/scripts/scripts-index.html)
  are provided with the starter code to help you execute the various [Maven](https://cs1302uga.github.io/cs1302-book/tools/maven/maven-index.html)
  commands with correct command-line arguments. Assuming you are in the top-level project directory,
  use the following commands to run Maven:

  * Clean and Compile:

    ```
    $ ./compile.sh
    ```

  * Clean, Compile, and Run:

    ```
    $ ./run.sh
    ```

  We encourage you to view the scripts, but you should not modify them. The graders will
  use the versions of these scripts provided with the starter code to compile and run
  your program.

* **Development Environment:** This project must *must compile and run*
  correctly on Odin using the specific version of Java that is enabled
  by the **CSCI 1302 shell profile**. For this requirement, the term
  *compile* should be interpreted as *compile with no errors or warnings*.

  If you decide to introduce additional `.java` files into your project,
  then they are expected to fulfill all non-functional and absolute requirements,
  even if the main parts of the project do not use them. This includes, but
  is not limited to the code style requirements. You may assume
  graders will compile your source code using Maven so that dependencies
  are handled automatically. You should remove any `.java` files that you
  do not need before submission.

* **No FXML or use of Scene Builder:** FXML and SceneBuilder
  are advanced tools that are not currently covered in this course. Use of
  either for this project is prohibited. Please note that the project is
  not easier with these tools. In most cases, they actually make the
  project harder, especially since those topics have not been covered.

* **No use of `JsonArray`, `JsonElement`, `JsonObject`, and `JsonParser`:**
  You may not use or mention the following classes provided by Gson:

  * `com.google.gson.JsonArray`
  * `com.google.gson.JsonElement`
  * `com.google.gson.JsonObject`
  * `com.google.gson.JsonParser`

  To parse a JSON-formatted string, use a `Gson` object's `fromJson` method to parse
  the string directly into instances of classes that represent the data. Classes for
  an iTunes Search response and result are provided with the starter code. Instructions
  for parsing JSON-formatted strings using `fromJson` is described in the
  [JSON chapter](https://cs1302uga.github.io/cs1302-book/java/json/json-index.html).

* **No use of the `openStream()` method in `URL`:**
  You may not use or mention the `openStream()` method provided by the `java.net.URL` class.
  If you need to access web content, then use an HTTP client as described in the
  [HTTP chapter](https://cs1302uga.github.io/cs1302-book/java/http/http-index.html).

### Grading

The graders will compile and run your code on Odin using Maven. They will test
each of the functional and non-functional requirements and total up the points
earned. This project is worth 100 points. Students have an opportunity to earn
up to an additional 10 points via extra credit.

# Appendix - Useful Links

## Third-Party APIs and Libraries

* [iTunes Search API](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/)
* [Google Gson Library](https://github.com/google/gson)


## Other

* [JavaScript Object Notation (JSON)](https://en.wikipedia.org/wiki/JSON)
* [URL Encoding](https://en.wikipedia.org/wiki/Percent-encoding)

<hr/>

[![License: CC BY-NC-ND 4.0](https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc-nd/4.0/)

<small>
Copyright &copy; Michael E. Cotterell and the University of Georgia.
This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a> to students and the public.
The content and opinions expressed on this Web page do not necessarily reflect the views of nor are they endorsed by the University of Georgia or the University System of Georgia.
</small>
