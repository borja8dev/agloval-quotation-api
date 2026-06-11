-- Phase E: Quotation Calculation Engine
ALTER TABLE quotation_lines ADD COLUMN IF NOT EXISTS discount_breakdown TEXT;

-- Migrate old status values to new enum
UPDATE quotations SET status = 'DRAFT' WHERE status = 'DONE';
UPDATE quotations SET status = 'ACCEPTED' WHERE status = 'CONFIRMED';
