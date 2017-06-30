# Speaker's List 
## Dynamically update a speaker's list based on the frequency at which representatives speak in an assembly over the course 
## of the year. 
### Created for the Case Western Reserve University Undergraduate Student Government. 

#### Parameters 
`GA.txt` This file needs to be initialized but will not need to be edited further. Should contain a line-delimited list of representatives 
in the assembly in the form `firstName lastName 0`.

#### Running 
Programs can be compiled using the following: 
`javac Assembly.java`
`javac SpeakersList.java`
`javac FrequencyStack.java`

During an assembly, first, execute the SpeakersList class with `java SpeakersList GA.txt`. 
When roll is called, click the name of representatives who are present. If you accidentally click a representative who isn't 
present, simply click their name again to undo. Once roll is called, click the button at the bottom. 

After the window closes, run the FrequencyStack class with `java FrequencyStack`. 
Click on names to add them to the speaker's list, which will appear on the right. To remove a representative from the 
speaker's list, click their name in the list. The list will dynamically resort itself based on the frequency at which 
representatives speak, updated every time they are added to the list. 

Once there has been a motion to adjourn, simply click the "adjourn" button.
