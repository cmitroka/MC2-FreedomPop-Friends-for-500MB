namespace FPFriender
{
    partial class frmMain
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
            this.cmdDoIt = new System.Windows.Forms.Button();
            this.tmrRunning = new System.Windows.Forms.Timer(this.components);
            this.txtLog = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.txtEmail = new System.Windows.Forms.TextBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.textBox3 = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.tmrAutoStart = new System.Windows.Forms.Timer(this.components);
            this.cmdClearCache = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.txtAccountsToUse = new System.Windows.Forms.TextBox();
            this.cmdAbout = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // cmdDoIt
            // 
            this.cmdDoIt.Location = new System.Drawing.Point(0, 9);
            this.cmdDoIt.Name = "cmdDoIt";
            this.cmdDoIt.Size = new System.Drawing.Size(75, 23);
            this.cmdDoIt.TabIndex = 0;
            this.cmdDoIt.Text = "Do It";
            this.cmdDoIt.UseVisualStyleBackColor = true;
            this.cmdDoIt.Click += new System.EventHandler(this.cmdDoIt_Click);
            // 
            // txtLog
            // 
            this.txtLog.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.txtLog.Location = new System.Drawing.Point(0, 114);
            this.txtLog.Multiline = true;
            this.txtLog.Name = "txtLog";
            this.txtLog.Size = new System.Drawing.Size(492, 56);
            this.txtLog.TabIndex = 57;
            this.txtLog.TabStop = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(192, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(126, 13);
            this.label1.TabIndex = 58;
            this.label1.Text = "Passed In Email Address:";
            // 
            // txtEmail
            // 
            this.txtEmail.Location = new System.Drawing.Point(324, 6);
            this.txtEmail.Name = "txtEmail";
            this.txtEmail.Size = new System.Drawing.Size(168, 20);
            this.txtEmail.TabIndex = 59;
            // 
            // textBox2
            // 
            this.textBox2.Enabled = false;
            this.textBox2.Location = new System.Drawing.Point(324, 33);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(168, 20);
            this.textBox2.TabIndex = 61;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Enabled = false;
            this.label2.Location = new System.Drawing.Point(231, 36);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(87, 13);
            this.label2.TabIndex = 60;
            this.label2.Text = "Accounts to use:";
            // 
            // textBox3
            // 
            this.textBox3.Enabled = false;
            this.textBox3.Location = new System.Drawing.Point(324, 59);
            this.textBox3.Name = "textBox3";
            this.textBox3.Size = new System.Drawing.Size(168, 20);
            this.textBox3.TabIndex = 63;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Enabled = false;
            this.label3.Location = new System.Drawing.Point(210, 62);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(108, 13);
            this.label3.TabIndex = 62;
            this.label3.Text = "Accounts passwords:";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(81, 41);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(92, 23);
            this.button1.TabIndex = 65;
            this.button1.Text = "Go To Friends";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // tmrAutoStart
            // 
            this.tmrAutoStart.Interval = 1;
            this.tmrAutoStart.Tick += new System.EventHandler(this.tmrAutoStart_Tick);
            // 
            // cmdClearCache
            // 
            this.cmdClearCache.Location = new System.Drawing.Point(81, 9);
            this.cmdClearCache.Name = "cmdClearCache";
            this.cmdClearCache.Size = new System.Drawing.Size(92, 23);
            this.cmdClearCache.TabIndex = 66;
            this.cmdClearCache.Text = "Clear Cache";
            this.cmdClearCache.UseVisualStyleBackColor = true;
            this.cmdClearCache.Click += new System.EventHandler(this.cmdClearCache_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Enabled = false;
            this.label4.Location = new System.Drawing.Point(231, 88);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(87, 13);
            this.label4.TabIndex = 67;
            this.label4.Text = "Accounts to use:";
            // 
            // txtAccountsToUse
            // 
            this.txtAccountsToUse.Location = new System.Drawing.Point(324, 85);
            this.txtAccountsToUse.Name = "txtAccountsToUse";
            this.txtAccountsToUse.Size = new System.Drawing.Size(168, 20);
            this.txtAccountsToUse.TabIndex = 68;
            // 
            // cmdAbout
            // 
            this.cmdAbout.Location = new System.Drawing.Point(0, 41);
            this.cmdAbout.Name = "cmdAbout";
            this.cmdAbout.Size = new System.Drawing.Size(75, 23);
            this.cmdAbout.TabIndex = 69;
            this.cmdAbout.Text = "Config...";
            this.cmdAbout.UseVisualStyleBackColor = true;
            this.cmdAbout.Click += new System.EventHandler(this.cmdAbout_Click);
            // 
            // frmMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(492, 169);
            this.Controls.Add(this.cmdAbout);
            this.Controls.Add(this.txtAccountsToUse);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.cmdClearCache);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.textBox3);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.txtEmail);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtLog);
            this.Controls.Add(this.cmdDoIt);
            this.Name = "frmMain";
            this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
            this.Text = "FPFriender";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button cmdDoIt;
        private System.Windows.Forms.Timer tmrRunning;
        public System.Windows.Forms.TextBox txtLog;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtEmail;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBox3;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Timer tmrAutoStart;
        private System.Windows.Forms.Button cmdClearCache;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtAccountsToUse;
        private System.Windows.Forms.Button cmdAbout;
    }
}

