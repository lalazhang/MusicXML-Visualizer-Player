# User Manual

**Table Of Contents**

# 1 Introduction

`TAB2XML` is Java/Gradle based tool that enables users to convert musical tablature from text to [MusicXML](https://www.musicxml.com/) **(*an open source standard for exchanging digital sheet music*) and enables them to play their converted file as well as visualize their file. This document outlines how a step-by-step guide to install and use the system for common use cases.

## 1.1 Intended Use

The intended audience and use for this application/document is mainly guided towards testers for this system, as well our customers. This should cover most of the relevant use cases for the most up to date version of our system, further changes will be reflected in this document.

## 1.2 Features & Overview

This app is built to be able to run offline and the user can interact with a graphical user interface that is simple and ease to use. The application will also allow the user to import a file of their choosing and export into a MusicXML file as well as the ability to play, pause, forward and rewind their converted audio file.

(**Note:** *As the product is under development and not been implemented completely, instruments of conversion are limited but will expand soon*)

# 2 System Requirements

The following requirements mentioned below are the minimum needs your system needs to satisfy before you can install or run the app:

- Java: v17+ (*JRE*)
- Gradle:  v7.3.3 or v7.1.1
- Operating System: Windows, Mac, Ubuntu (*any OS that supports the above requirements*)

# 3 Setup & Installation

`TAB2XML` is built as a Gradle project, and should work with any IDE/platform of your choice, but we will only go over installation in Eclipse.

## 3.1 Setup with Eclipse + Gradle

1. We need to begin by importing the project from GitHub , can be done by selecting `File > Import`
    
    ![Untitled](User%20Manua%2040f87/Untitled.png)
    
2. The next step would be selecting `Git > Projects from Git (with smart import)` from the menu provided
    
    ![Untitled](User%20Manua%2040f87/Untitled%201.png)
    
3. And then select `Clone URI` from the options available
    
    ![Untitled](User%20Manua%2040f87/Untitled%202.png)
    
4. This brings up a detailed menu with the information about the origin of the repository you’re trying to import in this case it would be - [github.com/devivekw/TAB2XML](https://github.com/devivekw/TAB2XML). Paste the link in the `URI` section and fill in the other sections if you wish to do so. (*Not required if you are just trying to setup*)
    
    ![Untitled](User%20Manua%2040f87/Untitled%203.png)
    
5. Once the project is successfully imported we would need perform the following three steps:
    1. Go to `Windows > Preference > Java > Installed JREs`  and make sure the default JRE is set to `v17` 
        
        ![Untitled](User%20Manua%2040f87/Untitled%204.png)
        
    2. Go to `Windows > Preference > Gradle`  and select `Gradle Version` to be 7.3.3 or 7.1.1
        
        ![Untitled](User%20Manua%2040f87/Untitled%205.png)
        
    3. Right click on the project folder and `Gradle > Refresh Gradle Project` make sure the Gradle Tasks are working 
        
        ![Untitled](User%20Manua%2040f87/Untitled%206.png)
        
6. In the Gradle Task window now you will be able to see various options and go ahead and select `TAB2XML > build > build` to build the project.
    
    ![Untitled](User%20Manua%2040f87/Untitled%207.png)
    

Following all the above steps, you should be able to successfully build the project. Then follow the steps below to run and use the application.

# 4 Usage

**Play the music**

1. After correctly setting up the project, simply start the application using the Gradle run command in `Gradle Tasks > TAB2XML_G14 > application > run`

![Double click on the run button to launch the application.](User%20Manua%2040f87/Untitled%208.png)

Double click on the run button to launch the application.

1. Upon launching the application, you will be greeted by the home screen where you can choose to either paste in the tablature into the white space or upload a .txt file from `File > Open...` and then selecting the desired file.

![TAB 2 XML home screen with sample tablature.](User%20Manua%2040f87/Untitled%209.png)

TAB 2 XML home screen with sample tablature.

1. Once your tablature is input into the application, it will be highlighted in yellow should there be an error with the input, and any lines highlighted in gray will be ignored by the application.

![The yellow shows there is an error as the final line is not closed with a line and will be ignored.](User%20Manua%2040f87/Untitled%2010.png)

The yellow shows there is an error as the final line is not closed with a line and will be ignored.

1. Should your tablature have no errors, you can continue with the other features of the application. You can view the XML notation of the tablature by clicking on the `Show MusicXML` button or save it using the `Save MusicXML` button.
2. If you are using a lengthier tablature, you can use the `Go To Measure` function to find a specific measure by entering what measure you are looking for and pressing the `Go` button.
3. Furthermore, you can play the music of the tablature you input using the `Play Music` button.

![A sample screenshot of the Music Player Window.](User%20Manua%2040f87/Untitled%2011.png)

A sample screenshot of the Music Player Window.

1. Simply press the play button to being the playback and press pause when you are satisfied.
2. Additionally, the Music Player has a `BPM` slider where you can customize the beats per minute of the playback if you wish.
3. Once you are finished with the program, you can press the `Exit` button in the music player and exit out of the program by clicking on the x button in the top right of the screen.

**View the music sheet**

1. After launching the App, Click open... button to import the txt file. 

![Screen Shot 2022-02-20 at 10.39.18 PM.png](User%20Manua%2040f87/Screen_Shot_2022-02-20_at_10.39.18_PM.png)

1. Now the text is loaded. To view the music score, Click Preview Sheet Music button.

![Screen Shot 2022-02-20 at 10.58.56 PM.png](User%20Manua%2040f87/Screen_Shot_2022-02-20_at_10.58.56_PM.png)

1. Since the imported music text is for instrument Drum. the clef is shown as percussion. We identified the position of notes based on their notes octave, for instance, we covered notes from C4 up to G5. We haven't not included note elements such as Duration, noteHead and Chord into our implementation , i.e. eighth, sixteenth, thus our music sheet does not show the variety of music notes yet. 

![Sample screenshot of the Sheet Music window.](User%20Manua%2040f87/Untitled%2012.png)

Sample screenshot of the Sheet Music window.