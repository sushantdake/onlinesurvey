package onlinesurveysystem;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
public class OnlineSurveySystem extends JFrame {
    private Map<String, String> userCredentials;
    private Connection connection;

    public OnlineSurveySystem() {
        userCredentials = new HashMap<>();
        userCredentials.put("user1", "password1"); // Sample user credentials
        userCredentials.put("user2", "password2");

        setTitle("Online Survey System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);
        JTextField usernameField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(usernameField, constraints);
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);
        JPasswordField passwordField = new JPasswordField(15);
        constraints.gridx = 1;
        //constraints.gridy = 1;
        panel.add(passwordField, constraints);
        // Panel for login and register buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(buttonPanel, constraints);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                connectToDatabase();
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(OnlineSurveySystem.this, "Login successful!");
                    // Open the survey dashboard or perform other actions after successful login
                    openDashboard();
                } else {
                    JOptionPane.showMessageDialog(OnlineSurveySystem.this, "Invalid username or password.");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(OnlineSurveySystem.this, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(OnlineSurveySystem.this, "Username already exists.");
                }
            }
        });
        //panel.add(registerButton);
        add(panel);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
    	try
    	{
    	PreparedStatement statement = connection.prepareStatement("SELECT * FROM usertableoss WHERE username = ? AND password = ?");
        statement.setString(1, username);
        statement.setString(2, password);

        // Execute query
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    	// If a record is found, return true; otherwise, return false
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Return false in case of any exception
    }
}

    private boolean registerUser(String username, String password) {
        if (!userCredentials.containsKey(username)) {
            userCredentials.put(username, password);
            return true;
        }
        return false;
    }
    private void connectToDatabase() {
        try {
            // Connect to your database
        	try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				 connection = DriverManager.getConnection("jdbc:ucanaccess://F://sushant/surveyDatabase51.accdb");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void openDashboard() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame dashboardFrame = new JFrame("Dashboard");
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);

                JButton surveyCreationButton = new JButton("Survey Creation");
               // surveyCreationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                surveyCreationButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openSurveyCreationFrame();
                    }
                });
                panel.add(surveyCreationButton,gbc);
                //panel.add(Box.createRigidArea(new Dimension(0, 10)));
                JButton surveyDistributionButton = new JButton("Survey Distribution");
                //surveyDistributionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                surveyDistributionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        openSurveyDistributionFrame();
                    }
                });
                panel.add(surveyDistributionButton,gbc);
                //panel.add(Box.createRigidArea(new Dimension(0, 10)));
                JButton responseCollectionButton = new JButton("Response Collection");
                //responseCollectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                responseCollectionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openResponseCollectionFrame();
                    }
                });
                panel.add(responseCollectionButton,gbc);
                //panel.add(Box.createRigidArea(new Dimension(0, 10)));
                JButton reportGenerationButton = new JButton("Report Generation");
               // reportGenerationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                reportGenerationButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openReportGenerationFrame();
                    }
                });
                JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
                buttonPanel.add(surveyCreationButton);
                buttonPanel.add(surveyDistributionButton);
                buttonPanel.add(responseCollectionButton);
                buttonPanel.add(reportGenerationButton);
                dashboardFrame.add(buttonPanel);
                dashboardFrame.pack();
                dashboardFrame.setLocationRelativeTo(null);
                dashboardFrame.setVisible(true);
            }
        });
    }
    private void openSurveyCreationFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               new SurveyCreationFrame(connection);
            }
        });
    }

    private void openSurveyDistributionFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Implement Survey Distribution Frame
            	new SurveyDistributionFrame(connection);
            }
        });
    }

    private void openResponseCollectionFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Implement Response Collection Frame
            	new ResponseCollectionFrame(connection);
            }
        });
    }

    private void openReportGenerationFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Implement Report Generation Frame
            	new ReportGenerationFrame(connection);
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineSurveySystem();
            }
        });
    }
        class Survey {
            private String title;
            private List<Question> questions;
            private User createdBy;

            public Survey(String title, List<Question> questions, User createdBy) {
                this.title = title;
                this.questions = questions;
                this.createdBy = createdBy;
            }

            // Getters and setters
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<Question> getQuestions() {
                return questions;
            }

            public void setQuestions(List<Question> questions) {
                this.questions = questions;
            }

            public User getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(User createdBy) {
                this.createdBy = createdBy;
            }
        }
         class Question {
            private String text;
            private Question type;

            public Question(String text, Question type) {
                this.text = text;
                this.type = type;
            }

            // Getters and setters
            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public Question getType() {
                return type;
            }

            public void setType(Question type) {
                this.type = type;
            }
        }
        class Response {
            private Question question;
            private String answer;
            private User respondent;

            public Response(Question question, String answer, User respondent) {
                this.question = question;
                this.answer = answer;
                this.respondent = respondent;
            }

            // Getters and setters
            public Question getQuestion() {
                return question;
            }

            public void setQuestion(Question question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public User getRespondent() {
                return respondent;
            }

            public void setRespondent(User respondent) {
                this.respondent = respondent;
            }
        
    }
        class User {
            private String username;
            private String password;

            public User(String username, String password) {
                this.username = username;
                this.password = password;
            }

            // Getters and setters
            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }
        }
        class SurveyCreationFrame extends JFrame {
            private Connection connection;
            private JTextField titleField;
            private JTextArea questionArea;
            private JButton addQuestionButton;
            private JButton saveSurveyButton; 
            private String[] questions;
            public SurveyCreationFrame(Connection connection) {
                this.connection = connection;

                setTitle("Survey Creation");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setSize(400, 300);
                setLocationRelativeTo(null);
                JPanel panel = new JPanel(new BorderLayout());

                // Survey Title
                JLabel titleLabel = new JLabel("Survey Title:");
                titleField = new JTextField(20);
                JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                titlePanel.add(titleLabel);
                titlePanel.add(titleField);

                // Questions
                JLabel questionLabel = new JLabel("Questions:");
                questionArea = new JTextArea(10, 30);
                JScrollPane scrollPane = new JScrollPane(questionArea);

                // Buttons
                addQuestionButton = new JButton("Add Question");
                addQuestionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addQuestion();
                    }
                });
                saveSurveyButton = new JButton("Save Survey");
                saveSurveyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveSurvey();
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(addQuestionButton);
                buttonPanel.add(saveSurveyButton);

                panel.add(titlePanel, BorderLayout.NORTH);
                panel.add(questionLabel, BorderLayout.CENTER);
                panel.add(scrollPane, BorderLayout.CENTER);
                panel.add(buttonPanel, BorderLayout.SOUTH);

                add(panel);
                setVisible(true);
            }

            private void addQuestion() {
                // Implement logic to add a question to the JTextArea
            	 String question = JOptionPane.showInputDialog(this, "Enter a question:");
            	    if (question != null && !question.isEmpty()) {
            	        questionArea.append(question + "\n");
            	    }
            	}
            

            private void saveSurvey() {
                // Implement logic to save the survey data to the database
            	 String title = titleField.getText().trim();
            	    String questionsText = questionArea.getText().trim();
            	    if (title.isEmpty() || questionsText.isEmpty()) {
            	        JOptionPane.showMessageDialog(this, "Survey title and questions cannot be empty.");
            	        return;
            }
            	    String[] questions = questionsText.split("\n");
            	    
       
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO surveyque (title, question) VALUES (?, ?)");
                for (String q : questions) {
                    statement.setString(1, title);
                    statement.setString(2, q);
                    statement.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Survey saved successfully!");
                // Optionally, clear the fields after saving
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while saving the survey.");
            }
        }

        private void clearFields() {
            titleField.setText("");
            questionArea.setText("");
        }
            }
        }
         class SurveyDistributionFrame extends JFrame {
            private Connection connection;
            private JTextField emailField;
            private JButton distributeButton;

            public SurveyDistributionFrame(Connection connection) {
                this.connection = connection;

                setTitle("Survey Distribution");
                setSize(400, 200);
                setLocationRelativeTo(null);
                JPanel panel = new JPanel(new BorderLayout());

                JLabel emailLabel = new JLabel("Recipient Email:");
                JTextField emailField = new JTextField(20);
                JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                emailPanel.add(emailLabel);
                emailPanel.add(emailField);

                distributeButton = new JButton("Distribute Survey");
                distributeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        distributeSurvey();
                    }
                });

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(distributeButton);

                panel.add(emailPanel, BorderLayout.NORTH);
                panel.add(buttonPanel, BorderLayout.CENTER);

                add(panel);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setVisible(true);
            }

            private void distributeSurvey() {
            	String recipientEmail = emailField.getText().trim();
                
                if (recipientEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter recipient email.");
                    return;
                }

                // Implement logic to distribute the survey (e.g., send email)
                try {
                    // Code to send email to recipientEmail
                    // Example:
                    // EmailSender.sendEmail(recipientEmail, "Survey Invitation", "Please participate in our survey!");
                    JOptionPane.showMessageDialog(this, "Survey distributed successfully to " + recipientEmail);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Failed to distribute survey: " + ex.getMessage());
                    ex.printStackTrace();
                // Implement logic to distribute the survey (e.g., send email)
            }
        
               // JPanel panel = new JPanel();
                //panel.setLayout(new GridLayout(2, 1));

               // JLabel titleLabel = new JLabel("Survey Distribution Frame");
                //titleLabel.setHorizontalAlignment(JLabel.CENTER);
               // panel.add(titleLabel);

                // Add distribution-related components here

                //add(panel);
                //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //setVisible(true);
            }
        }
         class ResponseCollectionFrame extends JFrame {
        	    private Connection connection;

        	    public ResponseCollectionFrame(Connection connection) {
        	        this.connection = connection;

        	        setTitle("Response Collection");
        	        setSize(400, 200);
        	        setLocationRelativeTo(null);

        	        JPanel panel = new JPanel(new BorderLayout());
        	        panel.setLayout(new GridLayout(2, 1));

        	        JLabel titleLabel = new JLabel("Response Collection Frame");
        	        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        	        panel.add(titleLabel);

        	        // Add response collection-related components here
        	        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        	       JButton collectResponsesButton = new JButton("Collect Responses");
        	        collectResponsesButton.addActionListener(e -> collectResponses());
        	        buttonPanel.add(collectResponsesButton);

        	        panel.add(buttonPanel,BorderLayout.CENTER);
        	        add(panel);
        	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	        setVisible(true);
        	        JFrame responseFrame = new JFrame("Survey Responses");
        	        responseFrame.setSize(600, 400);
        	        responseFrame.setLocationRelativeTo(null);
        	    }

        	        // Assuming you have a list of questions and their types
        	        private void collectResponses() {
        	            // Create a new frame for collecting responses
        	            JFrame responseFrame = new JFrame("Survey Responses");
        	            responseFrame.setSize(600, 400);
        	            responseFrame.setLocationRelativeTo(null);

        	            // Assuming you have a list of questions and their types
        	            List<Question> questions = getSurveyQuestions();

        	            // Create a panel to hold the survey questions
        	            JPanel questionPanel = new JPanel(new GridLayout(0, 1));

        	            // Iterate over each question and add UI components accordingly
        	            for (Question question : questions) {
        	                JLabel questionLabel = new JLabel(question.getText());
        	                questionPanel.add(questionLabel);

        	                if (question.getType().equals("Single Choice")) {
        	                    // Add radio buttons for single choice questions
        	                    ButtonGroup buttonGroup = new ButtonGroup();
        	                    for (String option : question.getOptions()) {
        	                        JRadioButton radioButton = new JRadioButton(option);
        	                        buttonGroup.add(radioButton);
        	                        questionPanel.add(radioButton);
        	                    }
        	                } else if (question.getType().equals("Multiple Choice")) {
        	                    // Add checkboxes for multiple choice questions
        	                    for (String option : question.getOptions()) {
        	                        JCheckBox checkBox = new JCheckBox(option);
        	                        questionPanel.add(checkBox);
        	                    }
        	                } else if (question.getType().equals("Text")) {
        	                    // Add a text field for text-based questions
        	                    JTextField textField = new JTextField(20);
        	                    questionPanel.add(textField);
        	                }
        	            }

        	            // Add a submit button to submit the responses
        	            JButton submitButton = new JButton("Submit Responses");
        	            submitButton.addActionListener(e -> {
        	                // Logic to retrieve responses from UI components and store them in the database
        	                storeResponsesInDatabase(questions, questionPanel);
        	                // Close the response frame after submission
        	                responseFrame.dispose();
        	            });

        	            // Add the question panel and submit button to the response frame
        	            responseFrame.add(questionPanel, BorderLayout.CENTER);
        	            responseFrame.add(submitButton, BorderLayout.SOUTH);

        	            // Make the response frame visible
        	            responseFrame.setVisible(true);
        	        }
        	        private void createResponseTable() {
        	            try 
        	            {
        	          Statement statement = connection.createStatement(); 
        	                String sql = "CREATE TABLE IF NOT EXISTS SurveyResponses ("
        	                        + "RespondentID INT NOT NULL,"
        	                        + "QuestionID INT NOT NULL,"
        	                        + "ResponseText VARCHAR(255),"
        	                        + "PRIMARY KEY (RespondentID, QuestionID)"
        	                        + ")";
        	                statement.executeUpdate(sql);
        	                System.out.println("SurveyResponses table created (if not exists).");
        	            } catch (SQLException e) {
        	                e.printStackTrace();
        	            }
        	        }
        	        private List<Question> getSurveyQuestions() {
						// TODO Auto-generated method stub
        	        	JTextField questionField = new JTextField();
        	            JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Single Choice", "Multiple Choice", "Text"});
        	            JTextField optionsField = new JTextField();

        	            JPanel panel = new JPanel(new GridLayout(0, 2));
        	            panel.add(new JLabel("Question:"));
        	            panel.add(questionField);
        	            panel.add(new JLabel("Type:"));
        	            panel.add(typeComboBox);
        	            panel.add(new JLabel("Options (comma-separated for Multiple Choice):"));
        	            panel.add(optionsField);

        	            int result = JOptionPane.showConfirmDialog(null, panel, "Add Survey Question", JOptionPane.OK_CANCEL_OPTION);
        	            if (result == JOptionPane.OK_OPTION) {
        	                String questionText = questionField.getText();
        	                String type = (String) typeComboBox.getSelectedItem();
        	                List<String> options = null;
        	                if (type.equals("Multiple Choice")) {
        	                    // Split the options by comma and trim whitespace
        	                    options = Arrays.stream(optionsField.getText().split(","))
        	                                    .map(String::trim)
        	                                    .collect(Collectors.toList());
        	                }

        	                // Create the Question object and return
        	                return Collections.singletonList(new Question(questionText, type, options));
        	            } else {
        	                // User canceled, return an empty list
        	                return Collections.emptyList();
        	            }
        	        
        	        }
         
        	       static class Question {
        	            private String text;
        	            private String type;
        	            private List<String> options;

        	            public Question(String text, String type, List<String> options) {
        	                this.text = text;
        	                this.type = type;
        	                this.options = options != null ? options : Collections.emptyList();
        	            }

        	            public String getText() {
        	                return text;
        	            }

        	            public String getType() {
        	                return type;
        	            }

        	            public List<String> getOptions() {
        	                return options;
        	            }
        	    }
        	   	private void storeResponsesInDatabase(List<Question> questions, JPanel questionPanel) {
    	            // Implement logic to retrieve responses from UI components and store them in the database
    	            // You can iterate over the questions and extract responses based on their types
    	            // Then, use the 'connection' object to interact with the database and store the responses
    	        

    	    }
         }
        	
         class ReportGenerationFrame extends JFrame {
        	    private Connection connection;

        	    public ReportGenerationFrame(Connection connection) {
        	        this.connection = connection;

        	        setTitle("Report Generation");
        	        setSize(400, 200);
        	        setLocationRelativeTo(null);

        	        JPanel panel = new JPanel();
        	        panel.setLayout(new GridLayout(2, 1));

        	        JLabel titleLabel = new JLabel("Report Generation Frame");
        	        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        	        panel.add(titleLabel);

        	        // Add report generation-related components here

        	        add(panel);
        	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	        setVisible(true);
        	    }
        	}
         
