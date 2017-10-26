namespace FPFriender
{
    partial class frmConfig
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
            this.label1 = new System.Windows.Forms.Label();
            this.txtVBS = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtReactivate = new System.Windows.Forms.TextBox();
            this.cmdOpenVBS = new System.Windows.Forms.Button();
            this.cmdOpenConfig = new System.Windows.Forms.Button();
            this.lblPrimary = new System.Windows.Forms.Label();
            this.lblReactivate = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 20);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(138, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "App Needs PrimaryScript.txt";
            // 
            // txtVBS
            // 
            this.txtVBS.Location = new System.Drawing.Point(238, 44);
            this.txtVBS.Multiline = true;
            this.txtVBS.Name = "txtVBS";
            this.txtVBS.Size = new System.Drawing.Size(147, 69);
            this.txtVBS.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 138);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(156, 13);
            this.label2.TabIndex = 4;
            this.label2.Text = "App Needs ReactivateScript.txt";
            // 
            // txtReactivate
            // 
            this.txtReactivate.Location = new System.Drawing.Point(238, 154);
            this.txtReactivate.Multiline = true;
            this.txtReactivate.Name = "txtReactivate";
            this.txtReactivate.Size = new System.Drawing.Size(147, 69);
            this.txtReactivate.TabIndex = 5;
            this.txtReactivate.Text = "Accounts to use - comma seperated: 01,02,03";
            // 
            // cmdOpenVBS
            // 
            this.cmdOpenVBS.Location = new System.Drawing.Point(310, 12);
            this.cmdOpenVBS.Name = "cmdOpenVBS";
            this.cmdOpenVBS.Size = new System.Drawing.Size(75, 23);
            this.cmdOpenVBS.TabIndex = 6;
            this.cmdOpenVBS.Text = "Open";
            this.cmdOpenVBS.UseVisualStyleBackColor = true;
            this.cmdOpenVBS.Click += new System.EventHandler(this.cmdOpenVBS_Click);
            // 
            // cmdOpenConfig
            // 
            this.cmdOpenConfig.Location = new System.Drawing.Point(310, 125);
            this.cmdOpenConfig.Name = "cmdOpenConfig";
            this.cmdOpenConfig.Size = new System.Drawing.Size(75, 23);
            this.cmdOpenConfig.TabIndex = 7;
            this.cmdOpenConfig.Text = "Open";
            this.cmdOpenConfig.UseVisualStyleBackColor = true;
            this.cmdOpenConfig.Click += new System.EventHandler(this.cmdOpenConfig_Click);
            // 
            // lblPrimary
            // 
            this.lblPrimary.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F);
            this.lblPrimary.Location = new System.Drawing.Point(15, 44);
            this.lblPrimary.Name = "lblPrimary";
            this.lblPrimary.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.lblPrimary.Size = new System.Drawing.Size(217, 69);
            this.lblPrimary.TabIndex = 8;
            this.lblPrimary.Text = "label3";
            // 
            // lblReactivate
            // 
            this.lblReactivate.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F);
            this.lblReactivate.Location = new System.Drawing.Point(15, 154);
            this.lblReactivate.Name = "lblReactivate";
            this.lblReactivate.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.lblReactivate.Size = new System.Drawing.Size(217, 69);
            this.lblReactivate.TabIndex = 9;
            this.lblReactivate.Text = "label3";
            // 
            // frmConfig
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(393, 235);
            this.Controls.Add(this.lblReactivate);
            this.Controls.Add(this.lblPrimary);
            this.Controls.Add(this.cmdOpenConfig);
            this.Controls.Add(this.cmdOpenVBS);
            this.Controls.Add(this.txtReactivate);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.txtVBS);
            this.Controls.Add(this.label1);
            this.Name = "frmConfig";
            this.Text = "Config";
            this.Load += new System.EventHandler(this.About_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtVBS;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtReactivate;
        private System.Windows.Forms.Button cmdOpenVBS;
        private System.Windows.Forms.Button cmdOpenConfig;
        private System.Windows.Forms.Label lblPrimary;
        private System.Windows.Forms.Label lblReactivate;
    }
}