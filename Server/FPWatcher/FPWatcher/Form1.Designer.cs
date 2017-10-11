namespace FPWatcher
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.tmrRunning = new System.Windows.Forms.Timer(this.components);
            this.label1 = new System.Windows.Forms.Label();
            this.txtFPFriendRequestLoc = new System.Windows.Forms.TextBox();
            this.cmdToggleRunning = new System.Windows.Forms.Button();
            this.txtFrienderEXELoc = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.cmdOpenConfig = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // tmrRunning
            // 
            this.tmrRunning.Enabled = true;
            this.tmrRunning.Interval = 1000;
            this.tmrRunning.Tick += new System.EventHandler(this.tmrRunning_Tick);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(13, 15);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(111, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Location of Requests:";
            // 
            // txtFPFriendRequestLoc
            // 
            this.txtFPFriendRequestLoc.Location = new System.Drawing.Point(130, 12);
            this.txtFPFriendRequestLoc.Name = "txtFPFriendRequestLoc";
            this.txtFPFriendRequestLoc.Size = new System.Drawing.Size(421, 20);
            this.txtFPFriendRequestLoc.TabIndex = 1;
            this.txtFPFriendRequestLoc.Text = "C:\\FPFriend Requests";
            // 
            // cmdToggleRunning
            // 
            this.cmdToggleRunning.Location = new System.Drawing.Point(246, 92);
            this.cmdToggleRunning.Name = "cmdToggleRunning";
            this.cmdToggleRunning.Size = new System.Drawing.Size(75, 23);
            this.cmdToggleRunning.TabIndex = 2;
            this.cmdToggleRunning.Text = "Toggle Running";
            this.cmdToggleRunning.UseVisualStyleBackColor = true;
            this.cmdToggleRunning.Click += new System.EventHandler(this.cmdToggleRunning_Click);
            // 
            // txtFrienderEXELoc
            // 
            this.txtFrienderEXELoc.Location = new System.Drawing.Point(130, 66);
            this.txtFrienderEXELoc.Name = "txtFrienderEXELoc";
            this.txtFrienderEXELoc.Size = new System.Drawing.Size(421, 20);
            this.txtFrienderEXELoc.TabIndex = 4;
            this.txtFrienderEXELoc.Text = "C:\\Program Files Portable\\FPFriender\\FPFriender.exe";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(13, 69);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(117, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Location of FPFriender:";
            // 
            // cmdOpenConfig
            // 
            this.cmdOpenConfig.Location = new System.Drawing.Point(13, 90);
            this.cmdOpenConfig.Name = "cmdOpenConfig";
            this.cmdOpenConfig.Size = new System.Drawing.Size(92, 23);
            this.cmdOpenConfig.TabIndex = 5;
            this.cmdOpenConfig.Text = "Open Settings";
            this.cmdOpenConfig.UseVisualStyleBackColor = true;
            this.cmdOpenConfig.Click += new System.EventHandler(this.cmdOpenConfig_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(12, 41);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(187, 13);
            this.label3.TabIndex = 6;
            this.label3.Text = "IMPORTANT!  Put in web.config also!";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(205, 38);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(346, 20);
            this.textBox1.TabIndex = 7;
            this.textBox1.Text = "<add key=\"FPFrienderRqPath\" value=\"C:\\<location>\"/>";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(563, 124);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.cmdOpenConfig);
            this.Controls.Add(this.txtFrienderEXELoc);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.cmdToggleRunning);
            this.Controls.Add(this.txtFPFriendRequestLoc);
            this.Controls.Add(this.label1);
            this.Name = "Form1";
            this.Text = "FP Watcher";
            this.Resize += new System.EventHandler(this.Form1_Resize);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Timer tmrRunning;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtFPFriendRequestLoc;
        private System.Windows.Forms.Button cmdToggleRunning;
        private System.Windows.Forms.TextBox txtFrienderEXELoc;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button cmdOpenConfig;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox textBox1;
    }
}

